package com.optimus.rpc.common;

/**
 * Created by Administrator on 2018/5/21.
 */
public interface Proxy {

    <T> T createProxy(Class<T> tClass);
}
