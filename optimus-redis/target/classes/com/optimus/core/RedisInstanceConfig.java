package com.optimus.core;

import java.util.List;

/**
 * redis 实例配置类
 * @author caoawei
 * Created on 2017/12/18.
 */
public class RedisInstanceConfig {

    private String host;
    private Integer port;
    private boolean readOnly;
    private int connectionTimeout;
    private List<RedisInstanceConfig> slaveConfg;

    public List<RedisInstanceConfig> getSlaveConfg() {
        return slaveConfg;
    }

    public void setSlaveConfg(List<RedisInstanceConfig> slaveConfg) {
        this.slaveConfg = slaveConfg;
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

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }
}
