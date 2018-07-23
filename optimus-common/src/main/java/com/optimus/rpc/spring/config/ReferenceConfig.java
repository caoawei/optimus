package com.optimus.rpc.spring.config;

import com.optimus.rpc.annotation.RpcReference;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by Administrator on 2018/5/23.
 */
public class ReferenceConfig extends AbstractConfig implements ApplicationContextAware {

    private String group;
    private String version;

    // 接口类型
    private String interfaceName;
    private Class interfaceClass;

    // 声明类
    private Class declaredClass;

    private ApplicationContext applicationContext;

    public ReferenceConfig(Class type,RpcReference rpcReference) {
        this.group = rpcReference.group();
        this.version = rpcReference.version();
        this.interfaceClass = type;
        this.interfaceName = type.getName();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

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

    public Class getDeclaredClass() {
        return declaredClass;
    }

    public void setDeclaredClass(Class declaredClass) {
        this.declaredClass = declaredClass;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
