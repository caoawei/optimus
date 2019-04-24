package com.souche.validation.core.annotation;

public @interface Min {

    double value();

    /**
     * 错误信息
     * @return string
     */
    String errorMsg() default "";
}
