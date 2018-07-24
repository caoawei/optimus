package com.optimus.rpc.spring.config;

import com.optimus.rpc.annotation.RpcReference;

/**
 * Created by Administrator on 2018/5/23.
 */
public class ReferenceBean<T> extends ReferenceConfig {

    public ReferenceBean (Class type,RpcReference rpcReference) {
        super(type,rpcReference);
    }
}
