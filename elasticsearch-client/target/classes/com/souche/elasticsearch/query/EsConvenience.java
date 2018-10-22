package com.souche.elasticsearch.query;

import java.util.Map;

/**
 * 基础操作: 转成Map对象
 * @author caoawei
 */
public interface EsConvenience<E> {

    /**
     * 绑定查询元素(建立各查询元素间的嵌套层次,需符合ES查询语法)
     * @param queryElement 查询元素
     * @return 调用此方法所属类对象
     */
    E bind(EsQueryElement queryElement);

    /**
     * 转换成Map对象
     * @return map对象
     */
    Map<String,Object> toMap();
}
