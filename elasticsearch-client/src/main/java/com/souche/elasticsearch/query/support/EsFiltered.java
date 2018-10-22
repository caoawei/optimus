package com.souche.elasticsearch.query.support;

import com.souche.elasticsearch.constant.QueryElementType;
import com.souche.elasticsearch.query.AbstractEsQueryElement;
import com.souche.elasticsearch.query.EsQueryElement;
import java.util.LinkedHashMap;
import java.util.Map;

public class EsFiltered extends AbstractEsQueryElement<EsFiltered> {

    private EsQueryElement queryElement;

    @Override
    public EsFiltered bind(EsQueryElement queryElement) {
        this.queryElement = queryElement;
        return this;
    }

    @Override
    public Map<String, Object> toMap() {
        if(queryElement == null) {
            return super.toMap();
        }

        if(QueryElementType.EMPTY.getName().equals(queryElement.typeName())) {
            return queryElement.toMap();
        }

        Map<String,Object> rs = new LinkedHashMap<>(1);
        rs.put(queryElement.typeName(),queryElement.toMap());
        return rs;
    }

    @Override
    public String typeName() {
        return QueryElementType.FILTERED.getName();
    }
}
