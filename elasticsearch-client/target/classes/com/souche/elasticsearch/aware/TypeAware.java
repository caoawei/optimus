package com.souche.elasticsearch.aware;

/**
 * 实现此接口,返回查询元素类型名
 * @author caoawei
 */
public interface TypeAware {

    /**
     * 返回类型名
     * @return 类型名
     */
    String typeName();
}
