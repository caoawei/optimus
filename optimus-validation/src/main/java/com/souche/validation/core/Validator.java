package com.souche.validation.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * 验证器,实际的注解执行逻辑.
 * 可通过自定义此接口的实现来处理自定义的验证注解
 * (自定义注解使用了{@link com.souche.validation.core.annotation.Validation}).
 * @param <T>
 */
public interface Validator<T extends Annotation> {

    /**
     * @return Return validation annotation type class of yours customize validator
     */
    Class<? extends Annotation> supportAnnotation();

    /**
     * 此方法用于处理方法参数上的注解
     * --------------------------------
     * eg.
     * <code>
     *     public void method1(@Min(18) @Max(65) int age,String name)
     * </code>
     * @param source 参数对象
     * @param annotation 参数对象上的注解
     */
    void validate(Object source,T annotation);

    /**
     * 此方法用于处理参数属性上的注解
     * -------------------------
     * eg.
     * <code>
     *     class Param {
     *         @Min(10)
     *         @Max(65)
     *         private int age;
     *
     *         private String name;
     *     }
     *
     *     ....
     *
     *     public void method1(Param param){
     *         ....
     *         ....
     *         ....
     *     }
     * </code>
     * @param source 参数对象
     * @param field 参数的属性
     * @param annotation 此属性上的注解
     */
    void validate(Object source, Field field,T annotation);
}
