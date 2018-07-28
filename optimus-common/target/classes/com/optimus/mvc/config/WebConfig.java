package com.optimus.mvc.config;

import com.optimus.mvc.handler.JsonResultReturnValueHandler;
import com.optimus.mvc.interceptor.PageInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 *  web配置
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(pageConfigInterceptor()).addPathPatterns("/**");
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        returnValueHandlers.add(jsonResultHandlerAdapter());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/service/script/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/service/script/js/");
    }

    private PageInterceptor pageConfigInterceptor(){
        return new PageInterceptor();
    }

    private JsonResultReturnValueHandler jsonResultHandlerAdapter(){
        return new JsonResultReturnValueHandler();
    }
}
