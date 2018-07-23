package com.optimus.rpc.config.service;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by Administrator on 2018/5/21.
 */
public class ServiceConfig {

    private String interfaceName;
    private Class interfaceClass;
    private List<Method> methods;

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public Class getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(Class interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public List<Method> getMethods() {
        return methods;
    }

    public void setMethods(List<Method> methods) {
        this.methods = methods;
    }
}
