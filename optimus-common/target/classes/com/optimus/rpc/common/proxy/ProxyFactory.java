package com.optimus.rpc.common.proxy;

import com.optimus.rpc.spring.config.ReferenceBean;

/**
 * Created by Administrator on 2018/5/23.
 */
public interface ProxyFactory {

    Object createProxy(Class<?> type);

    Object createProxy(ReferenceBean<?> referenceBean);
}
