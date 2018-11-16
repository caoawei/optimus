package com.optimus.redis.test;

import com.optimus.redis.config.RedisConfig;
import com.optimus.redis.config.RedisManager;
import com.optimus.redis.config.RedisServer;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import redis.clients.jedis.Jedis;

public class RedisMain {


    public static void main(String[] args) {
        String redisKey = "test-key-";
        int count = 5000;

        // 设置缓存
        testSet(redisKey,count);

        RedisConfig redisConfig = new RedisConfig();
        redisConfig.setHost("localhost");
        redisConfig.setPort(9101);
        redisConfig.setReadOnly(false);

        RedisConfig redisConfig2 = new RedisConfig();
        redisConfig2.setHost("localhost");
        redisConfig2.setPort(9101);
        redisConfig2.setReadOnly(false);

        RedisManager.appendNode(Collections.singletonList(redisConfig));

//        RedisManager.removeNode(Collections.singletonList("localhost:9105"));
//        RedisManager.register(Collections.singletonList(redisConfig2));

        // 增加一个节点之后,获取缓存,测试被影响缓存的数量
        afterAdd(redisKey,count);
    }

    public static void testSet(String baseKey,int count) {
        Map<String,Integer> map = new LinkedHashMap<>();
        for (int i = 0;i < count;i++) {
            String key = baseKey+i;
            RedisServer redisServer = RedisManager.routeRedisServer(key);

            Jedis jedis = redisServer.getClient();
            if(i == 5255) {
                System.out.println(jedis);
            }
            jedis.set(key,String.valueOf(i));

            Integer size = map.get(redisServer.hostAddress());
            map.put(redisServer.hostAddress(),size == null ? 1: (size+1));

            System.out.println("Redis-Key: 【"+key+"】 >>>> 路由选举: 【"+redisServer.hostAddress()+"】 >>>> Value: 【"+i+"】");
            jedis.close();
        }

        System.out.println("-----------------------------------------------------------------------------");

        for (Map.Entry<String,Integer> entry : map.entrySet()) {
            String rs = BigDecimal.valueOf(entry.getValue()/Double.valueOf(count)).setScale(2, RoundingMode.HALF_UP).toPlainString();
            System.out.println(entry.getKey()+"被选中概率: 【"+rs+"】");
        }
    }

    public static void afterAdd(String baseKey,int count) {
        int validCount = 0;
        for (int i = 0;i<count;i++) {
            String key = baseKey+i;
            RedisServer redisServer = RedisManager.routeRedisServer(key);
            try {
                Jedis jedis = redisServer.getClient();
                if(jedis != null && !jedis.exists(key)) {
                    validCount++;
                }

                jedis.close();
            } catch (Exception e) {
                System.out.println(e.getMessage()+"序号:"+i+", 主机: "+redisServer.hostAddress());
                break;
            }

        }

        System.out.println("----------------------新增节点后,缓存失效率--------------------------");

        System.out.println("受影响节点数据量: 【"+validCount+"】, 缓存失效率: 【"+ (BigDecimal.valueOf(validCount/Double.valueOf(count)).setScale(2,RoundingMode.HALF_UP).toPlainString()));
    }
}
