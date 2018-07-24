package com.optimus.doc.annotation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodDoc {

    /**
     * 方法描述
     * @return desc
     */
    String desc() default "";

    ParamDoc[] methodParam();
}
