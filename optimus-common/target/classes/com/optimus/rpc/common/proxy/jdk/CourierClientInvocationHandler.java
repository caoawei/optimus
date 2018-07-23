package com.optimus.rpc.common.proxy.jdk;

import com.optimus.common.exception.BizException;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 服务消费者调用远程服务接口时
 * Created on 2018/5/21.
 */
public class CourierClientInvocationHandler implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if(args != null && args.length > 0) {
            for (int i=0;i< args.length;i++) {
                if(!(args[i] instanceof Serializable)) {
                    throw new BizException("rpc invoke error: the param not implements serializable");
                }
            }
        }

        // 1.根据接口封装 Invockation对象

        // 2.根据Invockation对象 获得 Invoker 对象

        // 3. 调用 Invoker.invoke(method,args);

        // 4. 根据调用信息及使用的协议, 将调用信息及参数转成 协议报文: ProtocolMessage

        // 5. 调用 Protocol.send() 发送报文

        // 6. 根据使用的通讯框架,请求远程服务

        return null;
    }
}
