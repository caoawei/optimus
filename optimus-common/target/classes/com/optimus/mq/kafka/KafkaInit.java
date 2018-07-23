package com.optimus.mq.kafka;

import com.optimus.common.exception.BizException;
import com.optimus.mq.MessageConsumer;
import com.optimus.mq.TopicType;
import com.optimus.utils.ConfigUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class KafkaInit implements BeanFactoryPostProcessor {

    private static final Logger logger = LoggerFactory.getLogger(KafkaInit.class);
    private static ConcurrentHashMap<TopicType,List<MessageConsumer>> messageConsumers = new ConcurrentHashMap<>();
    private static ExecutorService ex;

    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public void init(){
        if(!KafkaPublisher.isEnableKafka()) {
            logger.info("当前系统未启用kafka");
            return;
        }
        Map<String,MessageConsumer> consumerBeans = beanFactory.getBeansOfType(MessageConsumer.class);
        if(consumerBeans == null || consumerBeans.isEmpty()){
            logger.info("当前系统未有消费者");
            return;
        }

        // kafka-broker 连通性测试
        if(!KafkaUtil.ping()){
            //throw new BizException("[kafka 连接失败或配置有误,请检查配置以及是否启动]");
        }

        for (Map.Entry<String,MessageConsumer> consumerEntry : consumerBeans.entrySet()){
            MessageConsumer mc = consumerEntry.getValue();
            List<MessageConsumer> list = messageConsumers.get(mc.supportTopicType());
            if(list == null){
                list = new ArrayList<>();
                messageConsumers.put(mc.supportTopicType(),list);
            }

            list.add(mc);
        }

        if(messageConsumers.size() > 0){
            // 主题的消息独占一个线程处理,因此这里线程的数量就是主题的数量
            // 如果信息量大的话,线程模型可以再套一层线程池
            // 即是: worker-job: worker 用来从kafka-broker拉取消息,job线程池处理消息,
            // 这样能够并发度,提高性能,但具体性能依赖于主机的整体性能,因为线程也是要消耗系统资源的.
            // 因线程池可以动态设置线程的数量,因此这一块完全可以采用动态配置,根据系统负载设置合适的大小
            // 可以通过配置文件的统一管理方式,再修改配置文件的情况下,及时的刷新或更改这些动态的配置
            // 实现方式可以通过zookeeper的节点watch机制以及redis轻量级的publish-subscribe
            ex = Executors.newFixedThreadPool(messageConsumers.size());
            for (Map.Entry<TopicType,List<MessageConsumer>> entry : messageConsumers.entrySet()){
                doConsume(entry.getKey(),entry.getValue());
            }
        }
    }

    private void doConsume(TopicType topicType, final List<MessageConsumer> consumers) {
        String env = ConfigUtil.getConfig("mq.kafka.env","");
        final String topic = env + "-" + topicType.getTopic();

        ex.submit(new Runnable() {
            @Override
            public void run() {
                while (true){
                    logger.info("[kafka-消费者]开始监听-GroupId:{},主题:{}",ConfigUtil.getConfig("spring.application.name"),topic);
                    KafkaConsumer<String,byte[]> kafkaConsumer = new KafkaConsumer<>(KafkaConfig.consumerConfig());
                    kafkaConsumer.subscribe(Arrays.asList(topic));

                    while (true){
                        try {
                            ConsumerRecords<String, byte[]> records = kafkaConsumer.poll(2000);
                            if(records != null && records.count() > 0){
                                Iterator<ConsumerRecord<String, byte[]>> iterator = records.iterator();
                                while (iterator.hasNext()){
                                    ConsumerRecord<String, byte[]> record = iterator.next();
                                    logger.info("[kafka 消费成功] 主题:{},分区:{},偏移:{}",topic,record.partition(),record.offset());
                                    for (MessageConsumer mc : consumers){
                                        try {
                                            mc.consume(new String(record.value(),"utf-8"));
                                        } catch (Exception e) {
                                            logger.error("[kafka 消费失败] 消费者:{},ERROR:{}",mc.getClass().getName(),e.getMessage(),e);
                                        }
                                    }
                                }

                                try {
                                    kafkaConsumer.commitSync();
                                } catch (Exception e){
                                    logger.error("[kafka 确认消息失败]主题:{},ERROR:{}",topic,e.getMessage(),e);
                                }
                            }
                        } catch (Exception ex){
                            if(ex instanceof ConnectException){
                                logger.error("[kafkfa]连接失败,主题:{}",topic);
                                try {
                                    logger.error("2秒后重连");
                                    Thread.sleep(2000);
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        });
    }
}
