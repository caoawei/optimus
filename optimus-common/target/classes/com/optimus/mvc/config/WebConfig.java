package com.optimus.mvc.config;

import com.optimus.mvc.customize.JsonHandlerMethodReturnValueHandler;
import com.optimus.mvc.interceptor.PageInterceptor;
import java.util.ArrayList;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 *  web配置
 */
public class WebConfig implements WebMvcConfigurer,WebMvcRegistrations {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(pageConfigInterceptor()).addPathPatterns("/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/service/script/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/service/script/js/");
    }

    /**
     * 这里是防止 {@link FeignClient} 和 {@link RequestMapping} 同时用于接口上时,导致启动错误(因为
     * 相同的路径会对应两个Controller,实现类会有RestController注解),
     * 这里同时也可以避免,消费者将FeignClient的注解的接口解析为Controller.
     * @return
     */
    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return new RequestMappingHandlerMapping() {
            @Override
            protected boolean isHandler(Class<?> beanType) {

                return (AnnotatedElementUtils.hasAnnotation(beanType, Controller.class) ||
                        (AnnotatedElementUtils.hasAnnotation(beanType, RequestMapping.class) && !AnnotatedElementUtils.hasAnnotation(beanType, FeignClient.class)));
            }
        };
    }

    /**
     * JSON统一返回结果定制.默认情况下自定义的HandlerMethodReturnValueHandler
     * 是放在末尾的,一般情况下执行不了
     * @return
     */
    @Override
    public RequestMappingHandlerAdapter getRequestMappingHandlerAdapter() {
        return new RequestMappingHandlerAdapter() {
            @Override
            public void afterPropertiesSet() {

                // 由父类方法加载所有的 HandlerMethodReturnValueHandler
                super.afterPropertiesSet();

                // 不可修改的List
                List<HandlerMethodReturnValueHandler> returnValueHandlers = super.getReturnValueHandlers();

                // returnValueHandlers副本
                List<HandlerMethodReturnValueHandler> newreturnValueHandlers = new ArrayList<>(returnValueHandlers);

                // 将自定义的Handler添加的首位
                newreturnValueHandlers.set(0,new JsonHandlerMethodReturnValueHandler());

                // 设置新值
                super.setReturnValueHandlers(newreturnValueHandlers);

            }
        };
    }

    private PageInterceptor pageConfigInterceptor(){
        return new PageInterceptor();
    }

}
