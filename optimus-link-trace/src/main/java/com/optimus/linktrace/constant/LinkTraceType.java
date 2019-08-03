package com.optimus.linktrace.constant;

/**
 * 链路追踪类型
 * @author caoawei
 */
public enum LinkTraceType {

    HTTP_CLIENT("http_client","HTTP客户端"),
    HTTP_SERVER("http_server","HTTP服务端"),
    MQ_CONSUMER("mq_consumer","MQ消费者"),
    MQ_PRODUCER("mq_producer","MQ生产者"),
    RPC_CONSUMER("rpc_producer","RPC服务消费者"),
    RPC_PROVIDER("rpc_provider","RPC服务提供者"),
    SQL("sql","数据库"),

    ;

    String value;
    String name;

    LinkTraceType(String value,String name) {
        this.value = value;
        this.name = name;
    }
}
