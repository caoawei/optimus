package com.souche.validation.core.spring;

import com.souche.validation.core.ValidationAttributeSource;
import java.lang.reflect.Method;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

/**
 * @see ValidationAttributeSource
 */
public class BeanFactoryValidationAdvisor extends AbstractBeanFactoryPointcutAdvisor {

    private ValidationAttributeSource validationAttributeSource;

    private StaticMethodMatcherPointcut pointcut = new StaticMethodMatcherPointcut() {
        @Override
        public boolean matches(Method method, Class<?> targetClass) {
            return validationAttributeSource.getValidationAttribute(method,targetClass) != null;
        }
    };

    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }

    public ValidationAttributeSource getValidationAttributeSource() {
        return validationAttributeSource;
    }

    public void setValidationAttributeSource(ValidationAttributeSource validationAttributeSource) {
        this.validationAttributeSource = validationAttributeSource;
    }
}
