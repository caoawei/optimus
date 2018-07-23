package com.luntan.module.user.mq;

import com.optimus.mq.MessageConsumer;
import com.optimus.mq.TopicType;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/3/31.
 */
@Component
public class UserSignConsumer implements MessageConsumer{
    private static int i=0;
    private static String val = "test";
    @Override
    public TopicType supportTopicType() {
        return TopicType.UserSign;
    }

    @Override
    public void consume(Object message) {
        System.out.println(message);
        ++i;
        System.out.println("==================================[     "+i+"     ]====================================");
    }

    public void test(){
        //int len = 100;
        int[] data = new int[100];
        Object[] data2 = new Object[50];
        for (int i=0;i<100;i++){
            data[i] = i;
            if(i < 50){
                data2[i]=i;
            }
            System.out.println(i);
        }
    }
}
