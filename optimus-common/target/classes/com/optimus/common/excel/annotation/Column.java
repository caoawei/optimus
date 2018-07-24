package com.optimus.common.excel.annotation;

import java.lang.annotation.*;

/**
 * 此注解表示类属性在excel中的列索引(从0开始,需依次编号,中间不能有间断)
 * Created on 2017/12/8 0008.
 */
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
    /**
     * 列索引值
     * @return
     */
    int order() default -1;

    /**
     * 对应的列标题(中或英文标题)
     * @return
     */
    String title() default "";
}
