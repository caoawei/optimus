package com.souche.validation.core;

import java.lang.reflect.Method;

public interface ValidationAttributeSource {

    /**
     * 获取验证属性
     * @param method 目标方法
     * @param targetClass 目标类
     * @return ValidationAttribute
     */
    ValidationAttribute getValidationAttribute(Method method, Class targetClass);
}
