//package com.optimus.rpc.registry.support;
//
//import com.optimus.rpc.common.ServiceInfo;
//import com.optimus.rpc.common.ServiceKey;
//import com.optimus.rpc.registry.Registry;
//import com.optimus.utils.Utils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.locks.ReentrantLock;
//
///**
// * Created on 2018/5/21.
// */
//public abstract class AbstractRegistry implements Registry {
//
//    protected static final Logger logger = LoggerFactory.getLogger(AbstractRegistry.class);
//
//    private ReentrantLock lock = new ReentrantLock();
//
//    // group::interface::provider
//    protected Map<ServiceKey,List<ServiceInfo>> courierServices;
//
//    public AbstractRegistry() {
//        courierServices = new ConcurrentHashMap<>();
//    }
//
//    @Override
//    public boolean register(List<ServiceInfo> serviceInfos) {
//        for (ServiceInfo serviceInfo : serviceInfos) {
//            register(serviceInfo);
//        }
//        return true;
//    }
//
//    @Override
//    public void remove(ServiceKey serviceKey) {
//        courierServices.remove(serviceKey);
//    }
//
//    @Override
//    public List<ServiceInfo> findService(ServiceKey serviceKey) {
//        List<ServiceInfo> checkRs = courierServices.get(serviceKey);
//        if(Utils.isEmpty(checkRs)) {
//            loadRemoteCentral(serviceKey);
//        }
//
//        return courierServices.get(serviceKey);
//    }
//
//    /**
//     * 更新本地服务提供者信息
//     * @param serviceInfos 服务信息
//     * @param serviceKey 服务key
//     */
//    protected void localUpdate(List<ServiceInfo> serviceInfos,ServiceKey serviceKey) {
//        lock.lock();
//        try {
//            if(Utils.isEmpty(serviceInfos)) {
//                return;
//            }
//
//            if(serviceKey != null) {
//                courierServices.put(serviceKey,serviceInfos);
//            }
//            // serviceKey==null,表明是 /group/interface/providers/ 的所有子节点
//            else {
//
//                for (ServiceInfo serviceInfo : serviceInfos) {
//                    ServiceKey itemKey = serviceInfo.getKey();
//                    if(courierServices.containsKey(itemKey)) {
//                        addIfAbsent(courierServices.get(itemKey),serviceInfo);
//                    } else {
//                        List<ServiceInfo> val = new ArrayList<>();
//                        val.add(serviceInfo);
//                        courierServices.put(itemKey,val);
//                    }
//                }
//            }
//
//        } catch (Exception e) {
//            logger.error("本地服务提供者信息更新失败:ERROR:{}",e.getMessage(),e);
//        } finally {
//            lock.unlock();
//        }
//    }
//
//    void addIfAbsent(List<ServiceInfo> serviceInfos,ServiceInfo serviceInfo) {
//        if(!serviceInfos.contains(serviceInfo)) {
//            serviceInfos.add(serviceInfo);
//        }
//    }
//
//    /**
//     * 从远程服务注册中心加载服务
//     * @param serviceKey 服务标识(/group/interfaceName/providers/xxxxxxxxx?version=version)
//     * @return
//     */
//    protected abstract void loadRemoteCentral(ServiceKey serviceKey);
//}
