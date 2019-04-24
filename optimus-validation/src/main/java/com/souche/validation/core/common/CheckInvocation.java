package com.souche.validation.core.common;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * 暂时未使用
 */
public class CheckInvocation implements Serializable {

    /**
     * 方法参数对象
     */
    private Object source;

    /**
     * 属性(所属source)
     */
    private Field field;

    /**
     * 属性值
     */
    private Object fieldValue;
}
