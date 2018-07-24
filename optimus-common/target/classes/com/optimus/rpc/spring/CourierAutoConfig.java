package com.optimus.rpc.spring;

import com.optimus.rpc.config.registry.RegistryConfig;
import com.optimus.rpc.spring.config.AnnotationConfig;
import com.optimus.rpc.spring.config.ConsumerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

/**
 * 自动启动Rpc
 * Created on 2018/5/21.
 */
@Configuration
public class CourierAutoConfig extends AnnotationConfig implements EnvironmentAware {

    private static final Logger logger = LoggerFactory.getLogger(CourierAutoConfig.class);
    private ConfigurableEnvironment configurableEnvironment;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        super.postProcessBeanFactory(beanFactory);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.configurableEnvironment = (ConfigurableEnvironment) environment;
    }

    private void registerRegistry(RegistryConfig registryConfig, ConfigurableListableBeanFactory beanFactory) {
        if(registryConfig == null) {
            logger.debug("rpc 注册中心未配置");
        }
        beanFactory.registerSingleton("courierRegistryConfig",registryConfig);
    }

    private void registerConsumerConfig(ConsumerConfig consumerConfig,ConfigurableListableBeanFactory beanFactory) {
        if(consumerConfig == null) {
            logger.debug("rpc 消费者未配置");
        }
    }

}
