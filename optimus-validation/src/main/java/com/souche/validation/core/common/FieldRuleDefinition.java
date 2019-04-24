package com.souche.validation.core.common;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

public class FieldRuleDefinition extends AbstractRuleDefinition {

    public FieldRuleDefinition (Method method, Class<?> targetClass, int argIndex,Class<?> source,Class<?> annotatedType,String name,List<Annotation> scanAnnotationList) {
        super(method,targetClass,argIndex,source,2,annotatedType,name,scanAnnotationList);
    }
}
