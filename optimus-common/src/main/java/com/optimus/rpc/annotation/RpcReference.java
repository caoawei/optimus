package com.optimus.rpc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 服务引用注解(服务消费者)
 * @author caoawei
 * Created on 2018/5/12.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RpcReference {
    /**
     * 服务版本号:用于接口有多个实现类
     * @return
     */
    String version() default "";

    /**
     * 服务所属组:用于接口有多个实现类
     * @return
     */
    String group() default "";
}
