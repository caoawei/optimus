package com.souche.validation.core.common;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

public class ParameterRuleDefinition extends AbstractRuleDefinition {

    public ParameterRuleDefinition (Method method, Class<?> targetClass, int argIndex,Class<?> source,Class<?> annotatedType,String name,List<Annotation> scanAnnotationList) {
        super(method,targetClass,argIndex,source,1,annotatedType,name,scanAnnotationList);
    }
}
