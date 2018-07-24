package com.optimus.common.support.zk.tools;

/**
 * Created by Administrator on 2018/5/18.
 */
public class StringDeserialization implements ZkDeserialization<String> {
    @Override
    public String deserializate(byte[] data) throws Exception {
        return (data == null || data.length == 0) ? "" : new String(data,"utf-8");
    }
}
