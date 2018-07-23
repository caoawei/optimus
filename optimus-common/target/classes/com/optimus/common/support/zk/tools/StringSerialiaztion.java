package com.optimus.common.support.zk.tools;

import com.optimus.utils.Utils;

/**
 * Created by Administrator on 2018/5/18.
 */
public class StringSerialiaztion implements ZkSerialization<String> {

    @Override
    public byte[] serialize(String data) throws Exception {
        return Utils.isNotEmpty(data) ? data.getBytes("utf-8") : null;
    }
}
