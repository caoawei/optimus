package com.souche.validation.core.common;

import com.souche.validation.core.RuleDefinition;
import com.souche.validation.core.ValidationAttribute;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

public class DefaultValidationAttribute implements ValidationAttribute {

    private Map<Integer,RuleDefinition> indexToRuleDefinitionMap = new LinkedHashMap<>();
    private Map<Class<?>,RuleDefinition> classToRuleDefinitionMap = new LinkedHashMap<>();

    private Method method;

    private Class<?> targetClass;

    public DefaultValidationAttribute(Method method,Class<?> targetClass) {
        this.method = method;
        this.targetClass = targetClass;
    }

    @Override
    public String getMethodName() {
        return method.getName();
    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public Class<?> getTargetClass() {
        return targetClass;
    }

    @Override
    public RuleDefinition getRuleDefinition(int argIndex) {
        return indexToRuleDefinitionMap.get(argIndex);
    }

    @Override
    public RuleDefinition getRuleDefinition(Class<?> parameterType) {
        return classToRuleDefinitionMap.get(parameterType);
    }

    @Override
    public Map<Integer, RuleDefinition> getRuleDefinitionOnParameter() {
        return indexToRuleDefinitionMap;
    }

    @Override
    public Map<Class<?>, RuleDefinition> getRuleDefinitionOnParameterField() {
        return classToRuleDefinitionMap;
    }

    public void addRuleDefinition(int argIndex,RuleDefinition rd) {
        indexToRuleDefinitionMap.put(argIndex,rd);
    }

    public void addRuleDefinition(Class<?> argType,RuleDefinition rd) {
        classToRuleDefinitionMap.put(argType,rd);
    }
}
