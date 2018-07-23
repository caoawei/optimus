package com.luntan.module;

import com.optimus.mq.MessageConsumer;
import com.optimus.mq.TopicType;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/3/31.
 */
@Component
public class AdminConsumer implements MessageConsumer {
    @Override
    public TopicType supportTopicType() {
        return TopicType.UserSign;
    }

    @Override
    public void consume(Object message) {
        System.err.println(message);
    }
}
