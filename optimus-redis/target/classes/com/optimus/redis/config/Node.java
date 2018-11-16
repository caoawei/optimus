package com.optimus.redis.config;

import lombok.Data;

/**
 * redis 配置
 * @author caoawei
 */
@Data
public class Node {

    /**
     * 节点hash值(每次都是相同的)
     */
    private Long id;

    /**
     * 与之映射的主机(host:port)
     */
    private String mappingHost;

}
