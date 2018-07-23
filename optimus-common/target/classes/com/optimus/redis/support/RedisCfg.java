package com.optimus.redis.support;

import java.util.List;

public class RedisCfg {

    private String host;

    private Integer port;

    private String slaveId;

    private RedisCfg redisSlaveCfg;

    public String getSlaveId() {
        return slaveId;
    }

    public void setSlaveId(String slaveId) {
        this.slaveId = slaveId;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public RedisCfg getRedisSlaveCfg() {
        return redisSlaveCfg;
    }

    public void setRedisSlaveCfg(RedisCfg redisSlaveCfg) {
        this.redisSlaveCfg = redisSlaveCfg;
    }
}
