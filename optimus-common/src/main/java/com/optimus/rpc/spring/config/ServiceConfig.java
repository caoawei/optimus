package com.optimus.rpc.spring.config;

import com.optimus.rpc.annotation.RpcService;

import java.util.Map;

/**
 * Created on 2018/5/22.
 */
public class ServiceConfig<T> extends AbstractConfig {

    private String group;
    private String version;

    // 接口类型
    private String interfaceName;
    private Class interfaceClass;
    // 实现类引用
    private T ref;
    // 属性引用远程服务bean
    private Map<String,ReferenceBean> referenceBeanMap;

    public ServiceConfig(RpcService rpcService) {
        this.group = rpcService.group();
        this.version = rpcService.version();
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

    public T getRef() {
        return ref;
    }

    public void setRef(T ref) {
        this.ref = ref;
    }

    public Map<String, ReferenceBean> getReferenceBeanMap() {
        return referenceBeanMap;
    }

    public void setReferenceBeanMap(Map<String, ReferenceBean> referenceBeanMap) {
        this.referenceBeanMap = referenceBeanMap;
    }
}
