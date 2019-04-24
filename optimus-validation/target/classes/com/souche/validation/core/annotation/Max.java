package com.souche.validation.core.annotation;

public @interface Max {

    double value();

    /**
     * 错误信息
     * @return string
     */
    String errorMsg() default "";
}
