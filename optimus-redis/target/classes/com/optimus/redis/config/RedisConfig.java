package com.optimus.redis.config;

import lombok.Data;

@Data
public class RedisConfig {
    /**
     * redis: host
     */
    private String host;

    /**
     * redis: port
     */
    private int port;

    /**
     * redis: auth
     */
    private String auth;

    /**
     * 是否只读
     */
    private boolean readOnly;

    /**
     * 是否是从节点
     */
    private boolean isSlave;

    /**
     * 主节点key
     */
    private String masterKey;

    /**
     * 从节点配置
     */
    private RedisConfig slaveConfig;

    public String hostAddress() {
        return host+":"+port;
    }
}
