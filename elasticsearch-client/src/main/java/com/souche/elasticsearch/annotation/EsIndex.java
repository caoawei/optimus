package com.souche.elasticsearch.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注释类或方法参数所对应的索引及文档类型
 * 注:
 * 优先级:
 * 参数级 > 类级
 * @author caoawei
 */
@Target({ElementType.TYPE,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EsIndex {

    /**
     * 索引名
     * @return
     */
    String index();

    /**
     * 文档类型
     * @return
     */
    String documentType();
}
