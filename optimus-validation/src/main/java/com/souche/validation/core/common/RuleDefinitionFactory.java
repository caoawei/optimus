package com.souche.validation.core.common;

import com.souche.validation.core.RuleDefinition;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

public class RuleDefinitionFactory {

    public static RuleDefinition createRuleDefinition(Method method, Class<?> targetClass,int argIndex,Class<?> source,int type, Class<?> annotatedType, String name,List<Annotation> scanAnnotationList) {
        if(type == 1) {
            return new ParameterRuleDefinition(method,targetClass,argIndex,source,annotatedType,name,scanAnnotationList);
        } else {
            return new FieldRuleDefinition(method,targetClass,argIndex,source,annotatedType,name,scanAnnotationList);
        }
    }
}
