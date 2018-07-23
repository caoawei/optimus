package com.optimus.common.support.zk.tools;

/**
 * Created by Administrator on 2018/5/18.
 */
public interface ZkDeserialization<T> {

    T deserializate(byte[] data) throws Exception;
}
