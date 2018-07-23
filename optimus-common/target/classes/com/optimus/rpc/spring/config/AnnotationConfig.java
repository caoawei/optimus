package com.optimus.rpc.spring.config;

import com.optimus.rpc.annotation.RpcReference;
import com.optimus.rpc.annotation.RpcService;
import com.optimus.rpc.spring.CourierAutoConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Created on 2018/5/23.
 */
public class AnnotationConfig extends AbstractConfig implements BeanFactoryPostProcessor,BeanPostProcessor,ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(CourierAutoConfig.class);
    private ConfigurableListableBeanFactory beanFactory;
    private ApplicationContext applicationContext;
    private Set<ServiceConfig> serviceConfigs;
    private Map<String,ReferenceBean> referenceBeanMap;
    private String[] annotationPackages;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(isMatch(bean.getClass().getName())) {
            return bean;
        }

        Field[] fields = bean.getClass().getDeclaredFields();
        if(fields != null && fields.length > 0) {
            for (int i=0;i<fields.length;i++) {
                Field field = fields[i];
                RpcReference rpcReference = field.getAnnotation(RpcReference.class);
                if(rpcReference != null) {
                    if(!field.isAccessible()) {
                        field.setAccessible(true);
                    }
                    ReferenceBean<Object> referenceConfig = new ReferenceBean<>(field.getType(),rpcReference);
                    referenceConfig.setDeclaredClass(bean.getClass());

                    // 生成引用代理
                    // 如果本地进程存在实现类,则直接本地引用
                    // 为远程时则生成代理类
                    refer(referenceConfig,field,bean);
                }
            }
        }

        return bean;
    }

    private void refer(ReferenceBean<Object> referenceConfig,Field field, Object bean) {
        try {
            Map<String,Object> typeMap = applicationContext.getBeansOfType(referenceConfig.getInterfaceClass());

            // 本地进程发现对象,则根据group及version找匹配的对象
            for (Map.Entry<String,Object> entry : typeMap.entrySet()) {
                Object obj = entry.getValue();
                RpcService rpcService = obj.getClass().getAnnotation(RpcService.class);
                if(rpcService != null) {
                    if(Objects.equals(referenceConfig.getGroup(),rpcService.group()) && Objects.equals(referenceConfig.getVersion(),rpcService.version())) {
                        ReflectionUtils.setField(field,bean,obj);
                    }
                }
            }
        } catch (Exception e) {
            // 本地未有引用对象,因此需要生成代理类
        }
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        RpcService rpcService = bean.getClass().getAnnotation(RpcService.class);
        if(rpcService != null) {
            ServiceBean<Object> serviceConfig = new ServiceBean<>(rpcService);
            serviceConfig.setRef(bean);
            serviceConfig.setApplicationContext(applicationContext);
            if(bean.getClass().getInterfaces().length > 0) {
                serviceConfig.setInterfaceClass(bean.getClass().getInterfaces()[0]);
            } else {
                throw new IllegalStateException("Failed to export remote service class "+bean.getClass().getName()+", cause: The @RpcService undefined interfaceClass or interfaceName, and the service class unimplemented any interfaces.");
            }
        }
        return bean;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private boolean isMatch(String packageName) {
        if(annotationPackages == null ||annotationPackages.length == 0) {
            return true;
        }

        for (int i=0;i<annotationPackages.length;i++) {
            String ap = annotationPackages[i];
            if(packageName.startsWith(ap)) {
                return true;
            }
        }

        return false;
    }

}
