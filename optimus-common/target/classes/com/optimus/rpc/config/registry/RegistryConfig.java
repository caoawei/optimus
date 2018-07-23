package com.optimus.rpc.config.registry;

import com.optimus.common.exception.BizException;
import com.optimus.rpc.spring.config.AbstractConfig;

/**
 * Created on 2018/5/21.
 */
public class RegistryConfig extends AbstractConfig {
    private static final String PROTOCOL_FLAG = "://";
    // 协议
    private String protocol;
    // 地址
    private String address;

    public RegistryConfig() {}

    public RegistryConfig(String address) {
        int index = address.indexOf(PROTOCOL_FLAG);
        if(index == 0) {
            throw new BizException("注册中心参数配置错误");
        }

        String protocol = address.substring(0,index);
        this.protocol = protocol;
        this.address = address.substring(index+PROTOCOL_FLAG.length(),address.length());
    }

    public RegistryConfig(String protocol,String address) {
        this.protocol = protocol;
        this.address = address;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
