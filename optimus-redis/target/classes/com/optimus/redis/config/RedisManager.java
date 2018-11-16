package com.optimus.redis.config;

import EDU.oswego.cs.dl.util.concurrent.ConcurrentHashMap;
import com.optimus.redis.RedisConfigRegistry;
import com.optimus.redis.config.reader.RedisConfigReader;
import com.optimus.redis.config.reader.support.XmlConfigReader;
import com.optimus.redis.util.HashAlgorithmUtils;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedisManager {

    private static Logger logger = LoggerFactory.getLogger(RedisManager.class);

    /**
     * 每个主节点存在200个虚拟节点
     */
    private static final int VIRTUAL_NODES_SIZE = 200;

    private static final int PER_GROUP_COUNT = 4;

    private static final String CONFIG_PATH = "redis-config.xml";

    private static RedisConfigReader configReader = new XmlConfigReader(CONFIG_PATH);

    private static RedisConfigRegistry redisConfigRegistry = new RedisConfigRegistry();

    private static Map<String,RedisServer> redisServerRegistry = new ConcurrentHashMap();

    private static TreeMap<Long,Node> nodeRegistry = new TreeMap<>();

    static {
        configReader.read(redisConfigRegistry);
        register(redisConfigRegistry.getRegistry());
    }

    /**
     * 路由选择redisServer
     * @param key
     * @return
     */
    public static RedisServer routeRedisServer(String key) {

        Objects.requireNonNull(key);

        RedisServer redisServer;

        // 将key映射到0-2^32空间上
        Long id = HashAlgorithmUtils.keyHash(key);

        /*
         * 根据id 寻找节点,规则如下:
         *
         * 1.查找是否存在节点id 大于或等于 此id,存在使用此节点
         * 2.如果1满足,则取第一个id(TreeMap是有顺序的)
         *
         * 3.前两步肯定会定位到一个节点,然后检测此节点是否可用
         *   如果不可用,则寻找大于此节点id的下一个节点,直到碰到可用的节点
         *   如果没有,则返回空值
         */
        Long targetId = id > nodeRegistry.lastKey() ? nodeRegistry.firstKey() : nodeRegistry.ceilingKey(id);

        do {
            redisServer = redisServerRegistry.get(nodeRegistry.get(targetId).getMappingHost());

            // 取id大于targetId的节点id
            targetId = nodeRegistry.higherKey(targetId);

        } while (!redisServer.isAlive() && targetId != null);  // targetId == null,说明遍历了所有节点,且每一个节点都不可用

        return redisServer;
    }

    /**
     * 新增物理节点
     * @param redisConfigs
     */
    public static void appendNode(List<RedisConfig> redisConfigs) {
        register(redisConfigs);
    }

    /**
     * 删除物理节点
     * @param hostAndPorts
     */
    public static void removeNode(List<String> hostAndPorts) {

        for (Iterator<Map.Entry<Long,Node>> iterator = nodeRegistry.entrySet().iterator();iterator.hasNext();) {
            Map.Entry<Long,Node> entry = iterator.next();
            for (String hostAndPort : hostAndPorts) {
                if(Objects.equals(hostAndPort,entry.getValue().getMappingHost())) {
                    iterator.remove();
                }
            }
        }

    }

    /**
     * 初始化时调用
     * @param redisConfigs
     */
    private static void register(List<RedisConfig> redisConfigs) {
        for (RedisConfig redisConfig : redisConfigs) {
            RedisServer redisServer = new RedisServer(redisConfig);
            redisServerRegistry.putIfAbsent(redisServer.hostAddress(),redisServer);

            // 每四个虚拟节点为一组,计算共有多少组
            int groupCount = VIRTUAL_NODES_SIZE / PER_GROUP_COUNT;

            for (int i = 0; i < groupCount; i++) {

                // 之所以每组四个,因为md5值占用128位,也就是16个字节
                // 每4个字节一组,4个字节刚好对应int类型,可以映射到0-2^32空间上
                byte[] digest = HashAlgorithmUtils.md5(key(redisConfig,i));

                for (int k = 0; k < PER_GROUP_COUNT; k++) {

                    // 存在环上的虚拟节点
                    Node node = new Node();
                    node.setMappingHost(redisConfig.hostAddress());

                    // 计算虚拟节点hash值
                    long id = (long) (digest[3+4*k] & 0xff) << 24;
                    id |= (long) (digest[2+4*k] & 0xff) << 16;
                    id |= (long) (digest[1+4*k] & 0xff) << 8;
                    id |= (long) (digest[4*k] & 0xff);

                    node.setId(id);

                    // 虚拟节点的id冲突的话,则只有首先占据坑位的节点有效,后续的均丢弃
                    // 也就是说总的虚拟节点并严格等于= 物理节点数*groupCount
                    // 暂时没想到好的方式处理这种冲突。

                    if(!nodeRegistry.containsKey(id)) {
                        nodeRegistry.put(id,node);
                    }

                    /*
                     * 针对节点id冲突的问题,有个不成熟的想法:
                     *
                     * 类似于HashMap使用连表解决冲突,
                     *
                     * 不过不是使用连表,是把冲突的节点id及其物理节点映射信息单独存储,
                     * 每次缓存操作时,分以下清况:
                     * 1. 写操作: 根据key选择出虚拟节点后,如果该节点存在冲突,则对这一组id相同的节点分别进行写操作
                     * 2. 读操作: 可对这一组id相同的节点采用一种算法，选择其一进行读,比如轮训,加权等
                     *
                     * 缓存操作的控制放到api层,这里只需要做冲突时的处理即可.
                     */
                }
            }
        }
    }

    private static String key(RedisConfig config,int i) {
        return config.getHost()+":"+config.getPort()+":"+i;
    }

}
