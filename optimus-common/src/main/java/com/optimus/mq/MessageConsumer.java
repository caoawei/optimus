package com.optimus.mq;

public interface MessageConsumer {

    TopicType supportTopicType();

    void consume(Object message);
}
