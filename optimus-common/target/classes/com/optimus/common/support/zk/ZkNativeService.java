package com.optimus.common.support.zk;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.List;

/**
 * zk原生服务接口
 * @author caoawei
 * Created on 2018/5/17.
 */
public interface ZkNativeService {

    /**
     * 创建节点
     * @param path 节点路径
     * @param data 节点数据
     * @param createMode 节点类型
     * @return 是否创建成功
     */
    boolean createNode(String path, byte[] data,CreateMode createMode);

    /**
     * 设置节点数据
     * @param path 节点路径
     * @param data 节点数据
     * @param version 节点版本号
     * @return 节点统计信息
     */
    Stat setData(String path,byte[] data,int version);

    /**
     * 删除节点
     * @param path 节点路径
     * @param version 节点版本号
     */
    void delete(String path,int version);

    /**
     * 检测节点是否存在
     * @param path 节点路径
     * @param watch 是否监听
     * @return 节点是否存在
     */
    Stat exists(String path,boolean watch);

    /**
     * 获取节点数据
     * @param path 节点路径
     * @param watch 是否监听节点数据变更
     * @return 节点数据
     */
    byte[] getData(String path,boolean watch);

    /**
     * 获取指定节点子节点
     * @param path 节点路径
     * @param watch 是否监听子节点变化
     * @return 所有子节点
     */
    List<String> getChildren(String path,boolean watch);
}
