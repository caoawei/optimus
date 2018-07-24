package com.optimus.common.support.zk;

import com.optimus.common.collection.impl.SimpleStack;
import com.optimus.common.exception.BizException;
import com.optimus.utils.Utils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author caoawei
 * Created on 2018/5/17.
 */
public class ZkService implements Watcher,ZkNativeService,ListenerRegister {
    private static final Logger logger = LoggerFactory.getLogger(ZkService.class);
    private ZooKeeper zk;
    private List<DataListener> dataListeners;
    private ReentrantLock writeLock = new ReentrantLock();
    private static ZkService instance;

    static {
        String connectStr = "127.0.0.1:2181";//ConfigUtil.getConfig("application.register.zk.server","127.0.0.1:2181");
        int sessionTimeout = 30000000;//ConfigUtil.getIntegerConfig("application.register.zk.session-timeout");
        instance = new ZkService(connectStr, sessionTimeout);
    }

    private ZkService(String connectStr,int sessionTimeout) {
        while (true) {
            try {
                zk = new ZooKeeper(connectStr,sessionTimeout,this);
                dataListeners = new ArrayList<>(4);
                break;
            } catch (Exception e) {
                if(e instanceof ConnectException) {
                    logger.error("zookeeper server配置不正确");
                } else {
                    logger.error("zookeeper 连接错误:{}",e.getMessage(),e);
                }

                try {
                    Thread.sleep(TimeUnit.SECONDS.toNanos(3));
                    continue;
                } catch (Exception ex) {
                    // ignore..
                }
            }
        }
    }

    public static ZkService getInstance() {
        return instance;
    }

    @Override
    public boolean createNode(String path, byte[] data, CreateMode createMode) {
        if(Utils.isEmpty(path)) {
            throw new IllegalArgumentException("path为空");
        }

        if(path.endsWith("/")) {
            path = path.substring(0,path.length()-1);
        }
        StringBuilder dyPath = new StringBuilder(path);

        // 自定义栈:非线程安全,用于存储待创建的节点路径层级名称(形如: /xxxx)
        com.optimus.common.collection.Stack<String> stack = new SimpleStack<>(2);

        // 加锁是为了在本机进程内避免不必要的重复创建过程,
        // 但仍无法避免多进程的重复创建的问题(在zookeeper上能保证只能创建一次,但需要处理创建失败的异常).
        writeLock.lock();
        try {

            // 节点的父节点存在性检测是从后到前,这样可最大限度的减少检测次数,
            // 因此这里使用栈,正好符合此操作逻辑的语义,是检测更加简单快捷,
            int index;
            for (;dyPath.length() > 0;) {
                if(exists(dyPath.toString(),true) == null) {
                    index = dyPath.lastIndexOf("/");

                    // 入栈是从年轻的父节点开始入栈
                    stack.push(dyPath.substring(index,dyPath.length()));
                    dyPath.delete(index,dyPath.length());
                    continue;
                }
                break;
            }

            // 整个节点都存在
            if(dyPath.length() == path.length()) {
                logger.error("zk节点:{}-已存在,创建失败",path);
                return false;
            }


            /*
             * 创建节点: 出栈的书序与入栈相反,从最古老的父节点开始出栈,按此语义依次创建节点路径
             */
            while (stack.size() > 0) {
                dyPath.append(stack.pop());
                boolean isEmpty = stack.isEmpty();
                try {
                    // 创建父path,其data=null,节点类型必须是永久节点(临时节点是不能够有子节点的)
                    zk.create(dyPath.toString(),isEmpty ? data : null, ZooDefs.Ids.OPEN_ACL_UNSAFE,isEmpty ? createMode : CreateMode.PERSISTENT);
                } catch (Exception e) {
                    if(e instanceof KeeperException) {

                        if(((KeeperException) e).code() == KeeperException.Code.NODEEXISTS) {

                        /* 节点已存在,这种情况会发生的
                        (存在同时创建该节点的多个线程或进程的时候,可能会发生) */

                            logger.error("zk节点:{} 已存在",dyPath);

                            // Nothing to do.
                        } else {
                            logger.error("zk节点:{} 创建失败,ERROR:{}",path,e.getMessage(),e);
                            return false;
                        }
                    }
                }
            }

            logger.info("zk节点:{} 创建成功",dyPath);
            return true;
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public Stat setData(String path,byte[] data, int version) {
        if(Utils.isEmpty(path)) {
            throw new IllegalArgumentException("path为空");
        }

        try {
            version = Math.max(version,1);
            return zk.setData(path,data,version);
        } catch (Exception e) {
            logger.error("zk节点:{} 设置数据失败,ERROR:{}",path,e.getMessage(),e);
        }

        return null;
    }

    @Override
    public void delete(String path, int version) {
        if(Utils.isEmpty(path)) {
            throw new IllegalArgumentException("path为空");
        }

        try {
            version = Math.max(1,version);
            zk.delete(path,version);
        } catch (Exception e) {
            logger.error("zk节点:{} 删除失败,ERROR:{}",path,e.getMessage(),e);
        }
    }

    @Override
    public Stat exists(String path, boolean watch) {
        if(Utils.isEmpty(path)) {
            throw new BizException("path为空");
        }

        if(!path.startsWith("/")) {
            path = "/"+path;
        }
        try {
            Stat stat = zk.exists(path,true);
            return stat;
        } catch (Exception e) {
            logger.error("检测zk节点:{} 存在性异常,ERROR:{}", path,e.getMessage(),e);
        }
        return null;
    }

    @Override
    public byte[] getData(String path, boolean watch) {
        try {
            Stat stat = exists(path,true);
            return stat == null ? null : zk.getData(path,watch,stat);
        } catch (Exception e) {
            logger.error("zk节点:{} 获取数据失败,ERROR:{}", path,e.getMessage(),e);
        }

        return null;
    }

    @Override
    public List<String> getChildren(String path, boolean watch) {
        try {
            return zk.getChildren(path,watch);
        } catch (Exception e) {
            logger.error("zk节点:{} 获取子节点失败,ERROR:{}", path,e.getMessage(),e);
        }

        return null;
    }

    @Override
    public void registerLinstener(DataListener dataListener) {
        if(dataListener == null) {
            throw new BizException("dataListener监听器为空");
        }
        dataListeners.add(dataListener);
    }

    @Override
    public void registerLinstener(Collection<DataListener> dataListeners) {
        if(dataListeners == null || dataListeners.isEmpty()) {
            throw new BizException("dataListener监听器为空");
        }
        dataListeners.addAll(dataListeners);
    }

    @Override
    public void process(WatchedEvent event) {
        if(event.getState() == Event.KeeperState.Disconnected) {
            logger.error("zk连接已断开,info:{}",event.toString());
            return;
        } else if(event.getState() == Event.KeeperState.AuthFailed) {
            logger.error("zk验证失败:auth-failed,info:{}",event.toString());
            return;
        }

        /* 处于正常连接状态时,则处理WatchedEvent事件 */
        if(event.getState() == Event.KeeperState.SyncConnected || event.getState() == Event.KeeperState.ConnectedReadOnly) {
            if(Utils.isNotEmpty(dataListeners)) {
                for (DataListener dl : dataListeners) {
                    dl.doWork(event);
                }
            }
        }
    }
}
