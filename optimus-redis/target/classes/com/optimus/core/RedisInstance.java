package com.optimus.core;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;

/**
 * @author caoawei
 * Created on 2017/12/18.
 */
public class RedisInstance {

    private JedisPool jedisPool;
    private RedisInstanceConfig config;
    private volatile boolean active;
    private List<RedisInstance> slaves;

    public RedisInstance(RedisInstanceConfig config){
        this.config = config;
        this.active = true;
        initRedisServer();
        initSlaveServers();
    }

    private void initSlaveServers() {
    }

    private void initRedisServer() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        this.jedisPool = new JedisPool(poolConfig,config.getHost(),config.getPort());
    }

    public Jedis getJedis(){
        return this.jedisPool.getResource();
    }

    public String getHost(){
        return config.getHost();
    }

    public Integer getPort(){
        return config.getPort();
    }

    public boolean isReadOnly(){
        return config.isReadOnly();
    }

    public RedisInstanceConfig getConfig() {
        return config;
    }

    public void setConfig(RedisInstanceConfig config) {
        this.config = config;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<RedisInstance> getSlaves() {
        return slaves;
    }

    public void setSlaves(List<RedisInstance> slaves) {
        this.slaves = slaves;
    }
}
