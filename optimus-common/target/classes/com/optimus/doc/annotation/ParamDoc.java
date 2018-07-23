package com.optimus.doc.annotation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ParamDoc {
    /**
     * 参数名
     * @return
     */
    String name() default "";

    /**
     * 参数描述
     * @return
     */
    String desc() default "";
}
