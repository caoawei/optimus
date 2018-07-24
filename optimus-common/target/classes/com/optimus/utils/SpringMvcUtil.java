package com.optimus.utils;

import org.springframework.context.ConfigurableApplicationContext;

public class SpringMvcUtil {

    private static ConfigurableApplicationContext context;

    public static void setApplicationContext(ConfigurableApplicationContext context){
        SpringMvcUtil.context = context;
    }

    public static <T> T getBean(Class<T> type){
        return context.getBean(type);
    }

    public static <T> T getBean(String beanName){
        return (T) context.getBean(beanName);
    }
}
