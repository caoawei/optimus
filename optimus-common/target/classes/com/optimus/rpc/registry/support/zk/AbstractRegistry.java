package com.optimus.rpc.registry.support.zk;

import com.optimus.rpc.common.ServiceInfo;
import com.optimus.rpc.common.ServiceKey;
import com.optimus.rpc.registry.Registry;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2018/7/11.
 */
public abstract class AbstractRegistry implements Registry{

    protected static final Logger logger = LoggerFactory.getLogger(AbstractRegistry.class);
    protected abstract void loadRemoteCentral(ServiceKey serviceKey);
    protected void localUpdate(List<ServiceInfo> serviceInfos,ServiceKey serviceKey){}

}
