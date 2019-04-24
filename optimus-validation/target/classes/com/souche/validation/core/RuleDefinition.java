package com.souche.validation.core;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * 规则定义
 * @author caoawei
 */
public interface RuleDefinition {

    /**
     * 获取方法名
     * @return method name
     */
    String getMethodName();

    /**
     * 方法所属类
     * @return class
     */
    Class<?> getMethodDeclaringClass();

    /**
     * 方法参数列表索引
     * @return int
     */
    int index();

    /**
     * 类型: 1|2 方法参数|对象属性
     * @return int
     */
    int getType();

    /**
     * @return return method parameter name or class field name
     */
    String getName();

    /**
     * 获取此规则作用的数据类型(方法参数或类属性)
     * @return class
     */
    Class<?> getAnnotatedType();

    /**
     * 获取作用在 {@link #getAnnotatedType()} 方法返回值上的注解列表
     * @return Annotation
     * @see #getAnnotatedType()
     */
    List<Annotation> getValidationAnnotationList();

    /**
     * 获取源：
     * 当且仅当getType() == 2时此方法才有返回
     * @return 属性所属对象
     */
    Class<?> getSource();
}
