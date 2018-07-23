package com.optimus.courier.registry;

import com.optimus.courier.common.CUrl;

/**
 * Created by caoawei on 2018/7/5.
 */
public interface Registry {

    void register(CUrl cUrl);

    CUrl findUrl(RegistryKey registryKey);
}
