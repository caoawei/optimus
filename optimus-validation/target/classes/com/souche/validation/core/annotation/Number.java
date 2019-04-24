package com.souche.validation.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD,ElementType.PARAMETER,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Number {

    /**
     * 数字类型,下限值(包含等于)
     * @return double
     */
    double min() default Double.MIN_VALUE;

    /**
     * 数字类型,上限值(包含等于)
     * @return double
     */
    double max() default Double.MAX_VALUE;

    /**
     * 错误信息
     * @return string
     */
    String errorMsg() default "";

}
