package com.optimus.rpc.registry;

import com.optimus.rpc.common.ServiceInfo;

/**
 * Created by Administrator on 2018/5/21.
 */
public interface ServiceCodec<T> {

    ServiceInfo decode(T info);

    T encode(ServiceInfo serviceInfo);
}
