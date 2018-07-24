package com.optimus.rpc.common;

import com.optimus.rpc.annotation.RpcService;

/**
 * 解析注解中version信息
 * @author caoawei
 * Created on 2018/5/12.
 */
public class Version {
    private static final String DEFAULT_VERSION = "1.0.0";

    public static String getVersion(RpcService service) {
        if(service == null) {
            throw new IllegalArgumentException("The parameter does not null:"+RpcService.class.getName());
        }

        return service.version() == null ? DEFAULT_VERSION : service.version();
    }
}
