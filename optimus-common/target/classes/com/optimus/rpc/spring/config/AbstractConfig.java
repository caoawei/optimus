package com.optimus.rpc.spring.config;

import java.io.Serializable;

/**
 * Created on 2018/5/22.
 */
public class AbstractConfig implements Serializable {

    protected boolean isPrimite(Class type) {
        return type.isPrimitive()
                || type == String.class
                || type == Character.class
                || type == Byte.class
                || type == Short.class
                || type == Integer.class
                || type == Long.class
                || type == Float.class
                || type == Double.class;
    }

}
