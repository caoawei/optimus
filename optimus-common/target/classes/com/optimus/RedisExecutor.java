package com.optimus;

import com.optimus.common.exception.BizException;
import com.optimus.utils.ConfigUtil;
import com.optimus.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

public class RedisExecutor {

    private static final Logger logger = LoggerFactory.getLogger(RedisExecutor.class);
    private static byte[] EX;
    private static byte[] PX;
    private static byte[] NX;
    private static byte[] XX;
    private static final int TIME_OUT = 3000;
    private static volatile boolean enable = false;
    private static JedisCluster jedisCluster;

    /**
     * 设置string类型缓存
     * @param key 缓存key
     * @param val 缓存值
     */
    public static void set(CacheKey key,String val) {
        jedisCluster.set(key.bytes(),val.getBytes());
    }

    /**
     * 设置string类型缓存,带有过期时间
     * @param key 缓存key
     * @param val 缓存值
     * @param second 过期时间(单位:秒)
     */
    public static void set(CacheKey key,String val,int second) {
        jedisCluster.setex(key.bytes(),second,val.getBytes());
    }

    /**
     * 设置string类型缓存,以NX模式设置,如果存在则不进行设置,适合于实现分布式锁
     * @param key 缓存key
     * @param val 缓存值
     */
    public static void setnx(CacheKey key,String val){
        jedisCluster.setnx(key.bytes(),val.getBytes());
    }

    /**
     * 设置string类型缓存,以NX模式设置,且带有过期时间
     * @param key 缓存key
     * @param val 缓存值
     * @param second 过期时间(单位:秒)
     * @see {{@link #setnx(CacheKey, String, int)}}
     * @see {{@link #set(CacheKey, String, int)}}
     */
    public static void setnx(CacheKey key,String val,int second){
        jedisCluster.set(key.bytes(),val.getBytes(),NX,EX,second);
    }

    private static boolean isEnable(){
        return enable;
    }

    static {
        String serverConfig = ConfigUtil.getConfig("redis.cluster.servers");
        if(Utils.isEmpty(serverConfig)) {
            logger.info("[redis节点未配置]");
        } else {
            Set<HostAndPort> hostAndPorts = new HashSet<>();
            String[] nodes = serverConfig.split(",");
            for (String node : nodes) {
                String[] nodeInfo = node.split(":");
                if(nodeInfo == null || nodeInfo.length == 1){
                    logger.error("[redis 配置无效,格式请参考(ip1:port1,ip2:port2,...)]");
                    throw new BizException("[redis 配置无效,格式请参考(ip1:port1,ip2:port2,...)]");
                }

                HostAndPort hostAndPort = new HostAndPort(nodeInfo[0],Integer.valueOf(nodeInfo[1]));
                hostAndPorts.add(hostAndPort);
            }

            jedisCluster = new JedisCluster(hostAndPorts,TIME_OUT);
            enable = true;

            EX = "EX".getBytes();
            PX = "PX".getBytes();
            NX = "NX".getBytes();
            XX = "XX".getBytes();
        }
    }
}
