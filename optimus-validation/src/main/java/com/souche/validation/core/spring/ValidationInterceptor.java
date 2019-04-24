package com.souche.validation.core.spring;

import com.souche.validation.core.ValidateHandlerAdapter;
import com.souche.validation.core.ValidationAttribute;
import com.souche.validation.core.ValidationAttributeSource;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * aop advice,实际做参数验证的类
 */
public class ValidationInterceptor implements MethodInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private ValidationAttributeSource validationAttributeSource;

    private ValidateHandlerAdapter handlerAdapter;

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {

        Class targetClass = methodInvocation.getThis() != null ? methodInvocation.getThis().getClass() : null;

        // Doing validate
        doValidate(methodInvocation.getMethod(), targetClass, methodInvocation.getArguments());

        return methodInvocation.proceed();
    }

    public ValidationAttributeSource getValidationAttributeSource() {
        return validationAttributeSource;
    }

    public void setValidationAttributeSource(ValidationAttributeSource validationAttributeSource) {
        this.validationAttributeSource = validationAttributeSource;
    }

    public ValidateHandlerAdapter getHandlerAdapter() {
        return handlerAdapter;
    }

    public void setHandlerAdapter(ValidateHandlerAdapter handlerAdapter) {
        this.handlerAdapter = handlerAdapter;
    }

    private void doValidate(Method method, Class targetClass, Object[] args) {

        if (args == null || args.length < 1) {
            return;
        }

        ValidationAttribute va = validationAttributeSource.getValidationAttribute(method, targetClass);
        if (va == null) {
            return;
        }

        logger.info("Validate on method {} of {} class", method.getName(), targetClass.getName());

        handlerAdapter.validate(va,args);
    }

}