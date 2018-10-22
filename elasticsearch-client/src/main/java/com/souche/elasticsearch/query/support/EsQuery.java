package com.souche.elasticsearch.query.support;

import com.souche.elasticsearch.constant.QueryElementType;
import com.souche.elasticsearch.query.AbstractEsQueryElement;
import com.souche.elasticsearch.query.EsQueryElement;
import java.util.LinkedHashMap;
import java.util.Map;

public class EsQuery extends AbstractEsQueryElement<EsQuery> {

    private EsQueryElement queryElement;

    @Override
    public EsQuery bind(EsQueryElement queryElement) {
        this.queryElement = queryElement;
        return this;
    }

    @Override
    public Map<String, Object> toMap() {
        if(this.queryElement == null) {
            return super.toMap();
        }

        if(QueryElementType.EMPTY.getName().equals(this.queryElement.typeName())) {
            return this.queryElement.toMap();
        } else {
            Map<String,Object> rs = new LinkedHashMap<>(1);
            rs.put(this.queryElement.typeName(),queryElement.toMap());
            return rs;
        }
    }

    @Override
    public String typeName() {
        return QueryElementType.QUERY.getName();
    }
}
