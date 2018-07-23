package com.optimus.mq.kafka;

import com.optimus.mq.TopicType;
import com.optimus.utils.ConfigUtil;
import com.optimus.utils.Utils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaPublisher {

    private static final Logger logger = LoggerFactory.getLogger(KafkaPublisher.class);

    private static KafkaProducer<String,byte[]> kafkaProducer;

    public static void publish(TopicType topicType, Object message){
        if(topicType == null){
            throw new NullPointerException("the topicType can not be null");
        }
        if(message == null){
            throw new NullPointerException("the message can not be null");
        }
        if(!isEnableKafka()){
            logger.error("[kafka 当前系统未启用]");
            return;
        }

        try {
            // kafka-broker 连通性测试
            if(!KafkaUtil.ping()){
                return;
            }
            if(kafkaProducer == null){
                synchronized (logger){
                    if(kafkaProducer == null){
                        kafkaProducer = new KafkaProducer<>(KafkaConfig.producerConfig());
                    }
                }
            }

            // 当前系统环境(考虑到实际多套环境版本不尽相同)
            String env = ConfigUtil.getConfig("mq.kafka.env","");
            String topic = env + "-" + topicType.getTopic();
            String data = Utils.toJson(message);
            ProducerRecord<String,byte[]> record = new ProducerRecord<String, byte[]>(topic,data.getBytes("utf-8"));

            logger.info("[kafka 开始发布消息.....]");
            kafkaProducer.send(record, (metadata,exception) -> {
                if(exception != null){
                    logger.error("[kafak 发布消息失败] cause:{}", exception.getMessage(),exception);
                    return;
                }
                    logger.info("[kafka 发布成功]: 主题:{},分区:{},偏移:{}",metadata.topic(),metadata.partition(),metadata.offset());
            });

        } catch (Exception e) {
            logger.error("[kafka 发布失败]: cause:{}",e.getMessage(),e);
            if(kafkaProducer != null){
                kafkaProducer.close();
                synchronized (logger){
                    kafkaProducer = null;
                }
            }
        }
    }

    public static boolean isEnableKafka(){
        return Utils.isNotEmpty(ConfigUtil.getConfig("mq.kafka.bootstrap.servers"));
    }
}
