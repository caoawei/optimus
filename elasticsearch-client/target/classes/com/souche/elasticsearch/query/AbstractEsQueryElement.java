package com.souche.elasticsearch.query;

import com.souche.elasticsearch.constant.QueryElementType;
import java.util.Collections;
import java.util.Map;

/**
 * 所有查询对象的超类,子类需适当的重写{@link #toMap()} and {@link #typeName()} 方法.
 * 查询类型参见:
 * @see QueryElementType
 */
public abstract class AbstractEsQueryElement<E> implements EsQueryElement<E> {

    @Override
    public Map<String, Object> toMap() {
        return Collections.emptyMap();
    }

    @Override
    public String typeName() {
        return QueryElementType.EMPTY.getName();
    }
}
