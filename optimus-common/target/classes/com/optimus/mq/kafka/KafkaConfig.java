package com.optimus.mq.kafka;

import com.optimus.utils.ConfigUtil;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaConfig {

    public static Properties consumerConfig(){
        Properties cfg = new Properties();
        cfg.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,ConfigUtil.getConfig("mq.kafka.bootstrap.servers"));
        cfg.put(ConsumerConfig.GROUP_ID_CONFIG, ConfigUtil.getConfig("spring.application.name"));
        cfg.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,"false");
        cfg.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
        cfg.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG,String.valueOf(1));
        cfg.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        cfg.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class.getName());
        return cfg;
    }

    public static Properties producerConfig(){
        Properties cfg = new Properties();
        cfg.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,ConfigUtil.getConfig("mq.kafka.bootstrap.servers"));
        cfg.put(ProducerConfig.ACKS_CONFIG, "all");
        cfg.put(ProducerConfig.RETRIES_CONFIG,3);
        cfg.put(ProducerConfig.BATCH_SIZE_CONFIG,1638);
        cfg.put(ProducerConfig.LINGER_MS_CONFIG,500);
        cfg.put(ProducerConfig.BUFFER_MEMORY_CONFIG,33554432);
        cfg.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        cfg.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getName());
        return cfg;
    }

}