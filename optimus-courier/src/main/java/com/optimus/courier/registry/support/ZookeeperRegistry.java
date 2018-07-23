package com.optimus.courier.registry.support;

import com.optimus.courier.common.CUrl;
import com.optimus.courier.registry.Registry;
import com.optimus.courier.registry.RegistryKey;

/**
 * Created by Administrator on 2018/7/9.
 */
public class ZookeeperRegistry implements Registry {
    @Override
    public void register(CUrl cUrl) {

    }

    @Override
    public CUrl findUrl(RegistryKey registryKey) {
        return null;
    }
}
