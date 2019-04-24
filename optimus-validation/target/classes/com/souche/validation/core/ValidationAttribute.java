package com.souche.validation.core;

import java.lang.reflect.Method;
import java.util.Map;

public interface ValidationAttribute {

    /**
     * 获取方法名
     * @return Method name
     */
    String getMethodName();

    /**
     * 获取方法
     * @return Method
     */
    Method getMethod();

    /**
     * 获取方法所属类
     * @return class
     */
    Class<?> getTargetClass();

    /**
     * 获取方法参数指定位置上的规则定义
     * @param argIndex 参数列表位置索引
     * @return RuleDefinition
     */
    RuleDefinition getRuleDefinition(int argIndex);

    /**
     * 获取方法指定参数类型上规则定义(属性field存在注解时使用)
     * @param parameterType 方法参数类型
     * @return RuleDefinition
     */
    RuleDefinition getRuleDefinition(Class<?> parameterType);

    /**
     * 获取方法参数上所有的验证规则定义
     * @return Map<Integer,RuleDefinition>
     */
    Map<Integer,RuleDefinition> getRuleDefinitionOnParameter();

    /**
     * 获取方法参数属性上所有的验证规则定义
     * @return Map<Class<?>,RuleDefinition>
     */
    Map<Class<?>,RuleDefinition> getRuleDefinitionOnParameterField();
}
