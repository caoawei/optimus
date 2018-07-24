package com.optimus.mq.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * kafka初始辅助类
 * @version 1.0
 * @author caoawei
 */
@Component
public class KafkaInitAider {

    @Autowired
    private KafkaInit kafkaInit;

    @PostConstruct
    public void init(){
        kafkaInit.init();
    }
}
