package com.optimus.mvc.extension;

import com.optimus.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.HandlerMethodReturnValueHandlerComposite;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.lang.reflect.Field;
import java.util.List;

//@Component
public class ApplicationContextExtension implements BeanFactoryPostProcessor {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationContextExtension.class);

    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public void init() {
        // RequestMappingHandlerAdapter 扩展
        extensionRequestMappingHandlerAdapter();
    }

    private void extensionRequestMappingHandlerAdapter() {
        logger.info("[自定义HandlerAdapter初始化.....]");
        RequestMappingHandlerAdapter requestMappingHandlerAdapter = (RequestMappingHandlerAdapter) beanFactory.getBean("requestMappingHandlerAdapter");
        handleRequestMappingHandlerAdapter(requestMappingHandlerAdapter);
    }

    private void handleRequestMappingHandlerAdapter(RequestMappingHandlerAdapter ha) {
        try {
            Field customReturnValueHandlersField = getField(ha.getClass(),"customReturnValueHandlers");
            Field returnValueHandlersField = getField(ha.getClass(),"returnValueHandlers");
            ReflectionUtils.makeAccessible(customReturnValueHandlersField);
            ReflectionUtils.makeAccessible(returnValueHandlersField);
            List<HandlerMethodReturnValueHandler> nativeCustomePropertyValue = (List<HandlerMethodReturnValueHandler>) ReflectionUtils.getField(customReturnValueHandlersField,ha);
            HandlerMethodReturnValueHandlerComposite nativeDefaultPropertyValue = (HandlerMethodReturnValueHandlerComposite) ReflectionUtils.getField(returnValueHandlersField,ha);
            if(Utils.isNotEmpty(nativeCustomePropertyValue)){
                Field realField = getField(HandlerMethodReturnValueHandlerComposite.class,"returnValueHandlers");
                ReflectionUtils.makeAccessible(realField);
                List<HandlerMethodReturnValueHandler> realValue = (List<HandlerMethodReturnValueHandler>) ReflectionUtils.getField(realField,nativeDefaultPropertyValue);

                for (HandlerMethodReturnValueHandler hma : nativeCustomePropertyValue){
                    if(realValue.contains(hma)){
                        realValue.remove(hma);
                    }
                }

                /**
                 * 因{@link HandlerMethodReturnValueHandlerComposite#returnValueHandlers}是final类型,因此new一个新对象替换
                 */
                HandlerMethodReturnValueHandlerComposite newHandlerComposite = new HandlerMethodReturnValueHandlerComposite();
                newHandlerComposite.addHandlers(nativeCustomePropertyValue);// 自定义HandlerMethodReturnValueHandler
                newHandlerComposite.addHandlers(realValue);// spring 默认HandlerMethodReturnValueHandler

                ReflectionUtils.setField(returnValueHandlersField,ha,newHandlerComposite);
                logger.info("[自定义HandlerAdapter初始化success");
            }

        } catch (Exception e) {
            logger.error(ApplicationContextExtension.class.getName()+" extension failure,detail info is {}",e.getMessage(),e);
        }
    }

    private Field getField(Class type,String fieldName) throws Exception {
        return type.getDeclaredField(fieldName);
    }

}
