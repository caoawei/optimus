package com.optimus.rpc.registry;

/**
 * Created by Administrator on 2018/5/18.
 */
public interface NodeInfo {

    /**
     * 返回和具体注册中心相关的key信息:
     * zookeeper:节点路径
     * @return
     */
    String keyInfo();



}
