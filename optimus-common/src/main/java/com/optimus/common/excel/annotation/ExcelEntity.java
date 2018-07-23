package com.optimus.common.excel.annotation;

import java.lang.annotation.*;

/**
 * 此注解表示一个类与excel的行对应,类似于orm映射
 * @author caoawei
 * Created 2017/12/8 0008.
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelEntity {
}
