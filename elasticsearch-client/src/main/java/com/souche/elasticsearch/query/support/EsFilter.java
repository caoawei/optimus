package com.souche.elasticsearch.query.support;

import com.souche.elasticsearch.constant.QueryElementType;
import com.souche.elasticsearch.query.AbstractEsQueryElement;
import com.souche.elasticsearch.query.EsQueryElement;
import java.util.LinkedHashMap;
import java.util.Map;

public class EsFilter extends AbstractEsQueryElement<EsFilter> {

    private EsQueryElement element;

    @Override
    public String typeName() {
        return QueryElementType.FILTER.getName();
    }

    @Override
    public EsFilter bind(EsQueryElement queryElement) {
        this.element = queryElement;
        return this;
    }

    @Override
    public Map<String, Object> toMap() {
        if(element != null) {
            Map<String,Object> map = new LinkedHashMap<>(1);
            map.put(element.typeName(),element.toMap());
            return map;
        } else {
            return super.toMap();
        }
    }
}
