package com.optimus.common.support.zk;

import org.apache.zookeeper.WatchedEvent;

/**
 * @author caoawei
 * Created on 2018/5/17.
 */
public interface DataListener {

    /**
     * 处理zk事件
     * @param event 事件类型
     */
    void doWork(WatchedEvent event);
}
