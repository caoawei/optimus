package com.optimus.redis.config;

import java.util.concurrent.atomic.AtomicLong;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisServer {

    private RedisConfig redisConfig;

    private volatile boolean alive;

    private AtomicLong handleRequestCount = new AtomicLong(0);

    private JedisPool masterJedisPool;

    private RedisServer slaveServer;

    public RedisServer(RedisConfig redisConfig) {
        this.redisConfig = redisConfig;
        masterJedisPool = new JedisPool(redisConfig.getHost(),redisConfig.getPort());
        alive = true;
    }

    public Jedis getClient() {

        Jedis client = masterJedisPool.getResource();

        // 主节点停机,则启动从节点(如果还未与从节点建立连接)
        if(!alive || client == null) {
            startSlave();
            client = slaveServer.alive ? slaveServer.getClient() : null;
        }
        increment();
        return client;
    }

    public boolean isAlive() {
        if(alive) {
            return alive;
        }

        startSlave();
        return slaveServer != null && slaveServer.alive;
    }

    public String hostAddress() {
        return redisConfig.getHost()+":"+redisConfig.getPort();
    }

    public long acceptCount() {
        return handleRequestCount.get();
    }

    public synchronized void restart() {
        masterJedisPool = new JedisPool(redisConfig.getHost(),redisConfig.getPort());
    }

    private synchronized void startSlave() {
        if(slaveServer == null) {
            try {
                slaveServer = new RedisServer(redisConfig.getSlaveConfig());
            } catch (Exception e) {
                // 从节点不可用
            }
        }
    }

    private void increment() {
        handleRequestCount.incrementAndGet();
    }
}
