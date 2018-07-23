package com.optimus.rpc.registry;

import com.optimus.rpc.common.ServiceInfo;
import com.optimus.rpc.common.ServiceKey;

import java.util.List;

/**
 * 服务注册与发现中心
 * @author caoawei
 * Created on 2018/5/12.
 */
public interface Registry {

    /**
     * 注册单个服务
     * @param serviceInfo 服务信息
     * @return true:注册成功;false:注册失败
     */
    boolean register(ServiceInfo serviceInfo);

    /**
     * 批量注册服务
     * @param serviceInfos 服务信息
     * @return true:注册成功;false:注册失败
     */
    boolean register(List<ServiceInfo> serviceInfos);

    /**
     * 移除服务
     * @param serviceKey 服务key:(group,interface,version)
     */
    void remove(ServiceKey serviceKey);

    /**
     * 发现服务
     * @param serviceKey 服务key:(group,interface,version)
     * @return
     */
    List<ServiceInfo> findService(ServiceKey serviceKey);
}
