package com.souche.elasticsearch.query;

import com.souche.elasticsearch.aware.TypeAware;

/**
 * 查询元素接口
 * @author caoawei
 * @param <E>
 */
public interface EsQueryElement<E> extends EsConvenience<E>, TypeAware {
}
