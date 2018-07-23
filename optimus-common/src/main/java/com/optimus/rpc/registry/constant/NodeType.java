package com.optimus.rpc.registry.constant;

/**
 * Created by Administrator on 2018/5/18.
 */
public enum NodeType {

    /**
     * 提供者
     */
    PROVIDERS("providers"),
    /**
     * 配置
     */
    CONFIGURATION("configuration"),
    /**
     * 消费
     */
    CONSUMERS("consumers"),

    ROUTERS("routers"),
    ;

    String code;
    NodeType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
