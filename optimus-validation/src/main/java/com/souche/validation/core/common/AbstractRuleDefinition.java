package com.souche.validation.core.common;

import com.souche.validation.core.RuleDefinition;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

public class AbstractRuleDefinition implements RuleDefinition {

    private Method method;

    private Class<?> targetClass;

    private Class<?> annotatedType;

    private int index;

    private int type;

    private List<Annotation> annotationList;

    private String name;

    private Class<?> source;

    protected AbstractRuleDefinition(Method method,Class<?> targetClass,int argIndex,Class<?> source,int type,Class<?> annotatedType,String name,List<Annotation> scanAnnotationList) {
        this.method = method;
        this.targetClass = targetClass;
        this.annotatedType = annotatedType;
        this.annotationList = scanAnnotationList;
        this.index = argIndex;
        this.name = name;
        this.type = type;
        this.source = source;
    }

    @Override
    public String getMethodName() {
        return method.getName();
    }

    @Override
    public Class<?> getMethodDeclaringClass() {
        return targetClass;
    }

    @Override
    public int index() {
        return index;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Class<?> getAnnotatedType() {
        return annotatedType;
    }

    @Override
    public List<Annotation> getValidationAnnotationList() {
        return annotationList;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public Class<?> getSource() {
        return source;
    }
}
