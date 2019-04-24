package com.souche.validation.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Text {

    /**
     * 最小长度,默认是不限制,此值须大于或等于0
     * @return int
     */
    int minLen() default -1;

    /**
     * 最大长度,默认是不限制,此值须大于或等于0
     * @return int
     */
    int maxLen() default -1;

    /**
     * 正则表达式
     * @return string
     */
    String pattern() default "";

    /**
     * 错误信息
     * @return string
     */
    String errorMsg() default "";
}
