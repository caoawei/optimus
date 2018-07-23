package com.optimus.rpc.spring.config;

import com.optimus.rpc.common.tools.IPUtil;
import com.optimus.rpc.config.registry.RegistryConfig;

/**
 * Created by Administrator on 2018/5/23.
 */
public class DefaultConfig {

    public static RegistryConfig defaultRegistryConfig() {
        return new RegistryConfig("zookeeper",IPUtil.localIp().getHostAddress()+":2181");
    }
}
