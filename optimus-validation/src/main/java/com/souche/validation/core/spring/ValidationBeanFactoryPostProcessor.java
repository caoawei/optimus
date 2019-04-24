package com.souche.validation.core.spring;

import com.souche.validation.core.ValidateHandlerAdapter;
import com.souche.validation.core.ValidationAttributeSource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;

public class ValidationBeanFactoryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // nothing to do
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

        String sourceBeanName = ValidationAttributeSource.class.getName();
        RootBeanDefinition attributeSource = new RootBeanDefinition(AnnotationValidationAttributeSource.class);
        attributeSource.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
        registry.registerBeanDefinition(sourceBeanName,attributeSource);

        String adapterName = ValidateHandlerAdapter.class.getName();
        RootBeanDefinition adapterBean = new RootBeanDefinition(DefaultValidateHandlerAdapter.class);
        attributeSource.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
        registry.registerBeanDefinition(adapterName,adapterBean);

        String interceptorName = ValidationInterceptor.class.getName();
        RootBeanDefinition validationInterceptor = new RootBeanDefinition(ValidationInterceptor.class);
        validationInterceptor.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
        validationInterceptor.getPropertyValues().add("validationAttributeSource",new RuntimeBeanReference(sourceBeanName));
        validationInterceptor.getPropertyValues().add("handlerAdapter",new RuntimeBeanReference(adapterName));

        registry.registerBeanDefinition(interceptorName,validationInterceptor);

        String advisorName = BeanFactoryValidationAdvisor.class.getName();
        RootBeanDefinition advisorDef = new RootBeanDefinition(BeanFactoryValidationAdvisor.class);
        advisorDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
        advisorDef.getPropertyValues().add("validationAttributeSource", new RuntimeBeanReference(sourceBeanName));
        advisorDef.getPropertyValues().add("adviceBeanName", interceptorName);
        registry.registerBeanDefinition(advisorName,advisorDef);
    }
}
