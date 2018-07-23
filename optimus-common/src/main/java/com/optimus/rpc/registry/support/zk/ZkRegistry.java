package com.optimus.rpc.registry.support.zk;

import com.optimus.common.support.zk.DataListener;
import com.optimus.rpc.common.ServiceInfo;
import com.optimus.rpc.common.ServiceKey;
import com.optimus.rpc.constant.Constant;
import com.optimus.rpc.registry.ServiceCodec;
import com.optimus.rpc.registry.constant.NodeType;
import com.optimus.utils.Utils;
import com.optimus.utils.ZkUtil;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * zookeeper 服务注册与发现中心
 * Created on 2018/5/21.
 */
public class ZkRegistry extends AbstractRegistry implements ServiceCodec<String> {

    private static final String DEFAULT_GROUP = "rpc";
    private static final String DEFAULT_VERSION = "1.0";

    public ZkRegistry() {
        ZkUtil.registerListener(new ZkDataListener());
    }

    @Override
    public boolean register(ServiceInfo serviceInfo) {
        if(Utils.isEmpty(serviceInfo.getGroup())) {
            serviceInfo.setGroup(DEFAULT_GROUP);
        }
        if(Utils.isEmpty(serviceInfo.getVersion())) {
            serviceInfo.setVersion(DEFAULT_VERSION);
        }

        String path = zkPath(serviceInfo.getKey(),NodeType.PROVIDERS);
        boolean rs = ZkUtil.createNode(path+encode(serviceInfo),null,true,null);
        localUpdate(Arrays.asList(serviceInfo),serviceInfo.getKey());
        return rs;
    }

    @Override
    public boolean register(List<ServiceInfo> serviceInfos) {
        return false;
    }

    @Override
    public void remove(ServiceKey serviceKey) {

    }

    @Override
    public List<ServiceInfo> findService(ServiceKey serviceKey) {
        return null;
    }

    @Override
    protected void loadRemoteCentral(ServiceKey serviceKey) {
        String path = zkPath(serviceKey,NodeType.PROVIDERS);
        if(Utils.isEmpty(path)) {
            logger.error("zk 节点路径不合法");
            throw new IllegalArgumentException("zk 节点路径不合法");
        }


        Stat stat = ZkUtil.exists(path,true);

        try {
            if(stat != null) {
                localUpdate(loadNodeInfo(path,serviceKey.getInterfaceName()),serviceKey);
            }
        } catch (Exception e) {
            logger.error("zk节点:{}-获取子节点异常,ERROR:{}",path,e.getMessage(),e);
        }
    }

    private List<ServiceInfo> loadNodeInfo(String path,String interfaceName) {
        List<String> nodeChildren = ZkUtil.getChildren(path,true);
        if(Utils.isEmpty(nodeChildren)) {
            return null;
        }

        List<ServiceInfo> rs = new ArrayList<>(nodeChildren.size());
        for (String nodeInfo : nodeChildren) {
            ServiceInfo serviceInfo = decode(nodeInfo);
            serviceInfo.setInterfaceName(interfaceName);
        }
        return rs;
    }

    @Override
    public ServiceInfo decode(String info) {
        ServiceInfo serviceInfo = new ServiceInfo();
        int index = info.indexOf("?");

        // 主机和端口号
        String hostAndPort = info.substring(0,index);
        // 额外信息
        String attInfo = info.substring(index+1,info.length());

        // 解析主机和端口号
        String[] hp = hostAndPort.split(":");
        serviceInfo.setHost(hp[0].trim());
        serviceInfo.setPort(Integer.valueOf(hp[1].trim()));

        // 额外信息解析
        String[] attArr = attInfo.split("#");
        for (int i = 0;i<attArr.length;i++) {
            String indexVal = attArr[i];
            int itemIndex = indexVal.indexOf("=");
            String pa = indexVal.substring(0,itemIndex);
            String val = indexVal.substring(itemIndex+1,indexVal.length());
            // applicationName
            if(Objects.equals(pa,Constant.PARAM_APP)) {
                serviceInfo.setApplicationName(val);
            }
            // group
            else if(Objects.equals(pa,Constant.PARAM_GROUP)) {
                serviceInfo.setGroup(val);
            }
            // version
            else if(Objects.equals(pa,Constant.PARAM_VERSION)) {
                serviceInfo.setVersion(val);
            }
            // methods
            else if(Objects.equals(pa,Constant.PARAM_METHODS)) {
                if(Utils.isNotEmpty(val)) {
                    String[] methods = val.split("#");
                    serviceInfo.setMethods(Arrays.asList(methods));
                }
            }
        }

        return serviceInfo;
    }

    @Override
    public String encode(ServiceInfo serviceInfo) {
        StringBuilder data = new StringBuilder("/");
        data.append(serviceInfo.getHost()).append(":");
        data.append(serviceInfo.getPort()).append("?");
        data.append(Constant.PARAM_APP).append("=").append(serviceInfo.getApplicationName());
        data.append(Constant.PARAM_DELIMITER).append(Constant.PARAM_INTERFACE).append("=").append(serviceInfo.getInterfaceName());
        if(Utils.isNotEmpty(serviceInfo.getGroup())) {
            data.append(Constant.PARAM_DELIMITER).append(Constant.PARAM_GROUP).append("=").append(serviceInfo.getGroup());
        }
        if(Utils.isNotEmpty(serviceInfo.getVersion())) {
            data.append(Constant.PARAM_DELIMITER).append(Constant.PARAM_VERSION).append("=").append(serviceInfo.getVersion());
        }
        if(Utils.isNotEmpty(serviceInfo.getMethods())) {
            List<String> mes = serviceInfo.getMethods();
            StringBuilder methodes = new StringBuilder();
            for (String item : mes) {
                methodes.append(item).append(Constant.PARAM_METHODS_DELIMITER);
            }

            int index = methodes.lastIndexOf("#");
            methodes.replace(index,index+1,"");
            data.append(Constant.PARAM_DELIMITER).append(Constant.PARAM_METHODS).append("=").append(methodes);
        }
        return data.toString();
    }

    /**
     * 根据serviceKey 和 NodeType生成zk 节点Path
     * @param serviceKey
     * @param nodeType
     * @see {@link NodeType}
     * @see {@link ServiceKey}
     * @return
     */
    private String zkPath(ServiceKey serviceKey,NodeType nodeType) {
        StringBuilder rs = new StringBuilder(Utils.isEmpty(serviceKey.getGroup()) ? Constant.ZK_ROOT : "/"+serviceKey.getGroup());
        rs.append(Constant.PATH_SEPERATOR).append(serviceKey.getInterfaceName());
        rs.append(Constant.PATH_SEPERATOR).append(nodeType==null?NodeType.PROVIDERS.getCode():nodeType.getCode());
        return rs.toString();
    }

    private class ZkDataListener implements DataListener {

        @Override
        public void doWork(WatchedEvent event) {
            if(Utils.isEmpty(event.getPath())) {
                return;
            }
            System.out.println("节点监听器捕获事件: path:"+event.getPath());
            Watcher.Event.EventType eventType = event.getType();
            switch (eventType) {
                case None:
                    break;
                case NodeCreated:
                    nodeCreated(event.getPath());
                    break;
                case NodeDataChanged:
                    nodeDataChanged(event.getPath());
                    break;
                case NodeChildrenChanged:
                    nodeChildrenChanged(event.getPath());
                    break;
                case NodeDeleted:
                    nodeDeleted(event.getPath());
                    break;
            }
        }

        void nodeCreated(String path) {
            if(!path.endsWith(NodeType.PROVIDERS.getCode())) {
                return;
            }

            int index = path.lastIndexOf("/");
            path = path.substring(0,index);
            List<String> nodeChildren = ZkUtil.getChildren(path,true);
            parseToServiceInfo(nodeChildren);
        }

        void nodeDataChanged(String path) {
            // nothing to do
        }

        void nodeChildrenChanged(String path) {
            List<String> nodeChildren = ZkUtil.getChildren(path,true);
            parseToServiceInfo(nodeChildren);
        }

        void nodeDeleted(String path) {
            // nothing
        }

        /**
         * 将zk中的数据解析成{@link com.optimus.rpc.common.ServiceInfo}
         * @param zkData 节点数据集
         */
        void parseToServiceInfo(List<String> zkData) {
            if(Utils.isEmpty(zkData)) {
                return;
            }

            List<ServiceInfo> rs = new ArrayList<>();
            for (String nodeInfo : zkData) {
                rs.add(decode(nodeInfo));
            }

            localUpdate(rs,null);
        }

        boolean checkPath(String path) {
            return false;
        }
    }
}
