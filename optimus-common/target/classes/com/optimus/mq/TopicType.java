package com.optimus.mq;

public enum TopicType {

    UserSign("user.sign")
    ,
    ;

    private String topic;

    TopicType(String topic){
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
