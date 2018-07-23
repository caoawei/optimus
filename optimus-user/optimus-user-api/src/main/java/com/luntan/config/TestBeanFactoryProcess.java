package com.luntan.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/4.
 */
@TestAnnotation
public class TestBeanFactoryProcess implements BeanDefinitionRegistryPostProcessor {
    private ConfigurableListableBeanFactory beanFactory;

    private List<TestService> list;
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
        Map<String, TestService> map = beanFactory.getBeansOfType(TestService.class);
        if(map != null){
            list = new ArrayList<>(map.size());
            //list.add(map.get());
            System.out.println(map);
        }
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        int count = registry.getBeanDefinitionCount();
        System.out.println(count);
    }
}
