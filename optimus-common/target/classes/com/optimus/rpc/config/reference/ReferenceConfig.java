package com.optimus.rpc.config.reference;

/**
 * Created by Administrator on 2018/5/21.
 */
public class ReferenceConfig {

    private String interfaceName;
    private Class interfaceClass;

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
}
