package com.optimus.rpc.common.proxy.jdk;

import com.optimus.rpc.common.proxy.ProxyFactory;
import com.optimus.rpc.spring.config.ReferenceBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Administrator on 2018/5/21.
 */
public class JDKProxyFactory implements ProxyFactory {

    private static final Logger logger = LoggerFactory.getLogger(JDKProxyFactory.class);

    @Override
    public Object createProxy(Class<?> type) {
        return null;
    }

    @Override
    public Object createProxy(ReferenceBean<?> referenceBean) {
        return Proxy.newProxyInstance(referenceBean.getInterfaceClass().getClassLoader(),new Class[]{referenceBean.getInterfaceClass()},new CourierInvocationHandler(referenceBean));
    }

    private static class CourierInvocationHandler implements InvocationHandler {

        private ReferenceBean referenceBean;

        CourierInvocationHandler(ReferenceBean referenceBean) {
            this.referenceBean = referenceBean;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return null;
        }
    }
}
