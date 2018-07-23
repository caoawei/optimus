package com.optimus.common.support.zk.tools;

/**
 * Created by Administrator on 2018/5/18.
 */
public interface ZkSerialization<T> {

    byte[] serialize(T data) throws Exception;
}
