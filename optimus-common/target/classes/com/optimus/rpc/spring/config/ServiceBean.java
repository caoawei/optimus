package com.optimus.rpc.spring.config;


import com.optimus.rpc.annotation.RpcService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by Administrator on 2018/5/23.
 */
public class ServiceBean<T> extends ServiceConfig implements ApplicationContextAware {


    private ApplicationContext applicationContext;

    public ServiceBean(RpcService rpcService) {
        super(rpcService);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


}
