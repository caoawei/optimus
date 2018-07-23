package com.optimus.utils;

import com.optimus.common.support.zk.DataListener;
import com.optimus.common.support.zk.ZkService;
import com.optimus.common.support.zk.tools.StringDeserialization;
import com.optimus.common.support.zk.tools.StringSerialiaztion;
import com.optimus.common.support.zk.tools.ZkDeserialization;
import com.optimus.common.support.zk.tools.ZkSerialization;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;


/**
 * @author caoawei
 * Created on 2018/5/17.
 */
public class ZkUtil {
    private static final Logger logger = LoggerFactory.getLogger(ZkUtil.class);
    private static ZkService zkService;
    public static ZkSerialization DEFAULT_ZKSERIAL = new StringSerialiaztion();
    public static ZkDeserialization DEFAULT_ZKDESERIAL = new StringDeserialization();

    static {
        zkService = ZkService.getInstance();
    }

    public static void registerListener(DataListener dataListener) {
        zkService.registerLinstener(dataListener);
    }

    public static <T> boolean createNode(String path,T data,boolean isEphemeral,ZkSerialization<T> serialization) {
        try {
            return zkService.createNode(path,serialization == null ? DEFAULT_ZKSERIAL.serialize(data) : serialization.serialize(data),isEphemeral ? CreateMode.EPHEMERAL : CreateMode.PERSISTENT);
        } catch (Exception e) {
            logger.error("zk节点:{}-创建失败,ERROR:{}", path,e.getMessage(),e);
            return false;
        }
    }

    public static <T> T getData(String path, boolean watch, ZkDeserialization<T> deserialization) {
        byte[] data = zkService.getData(path,watch);
        try {
            return deserialization.deserializate(data);
        } catch (Exception e) {
            logger.error("zk节点:{}-数据转换失败:{}",path,e.getMessage(),e);
        }
        return null;
    }

    public static <T> Stat setData(String path, T data, ZkSerialization<T> serialization) {
        try {
            return zkService.setData(path,serialization.serialize(data),0);
        } catch (Exception e) {
            logger.error("zk节点:{}-设置数据失败:{}",path,e.getMessage(),e);
        }

        return null;
    }

    public static Stat exists(String path,boolean watch) {
        return zkService.exists(path,watch);
    }

    public static List<String> getChildren(String path, boolean watch) {
        return zkService.getChildren(path,watch);
    }
}
