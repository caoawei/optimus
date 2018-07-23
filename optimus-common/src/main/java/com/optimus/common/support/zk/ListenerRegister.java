package com.optimus.common.support.zk;

import java.util.Collection;

/**
 * 事件注册接口
 * @author caoawei
 * Created on 2018/5/17.
 */
public interface ListenerRegister {

    void registerLinstener(DataListener dataListener);

    void registerLinstener(Collection<DataListener> dataListeners);

}
