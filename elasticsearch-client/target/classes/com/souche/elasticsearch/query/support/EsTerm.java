package com.souche.elasticsearch.query.support;

import com.souche.elasticsearch.constant.QueryElementType;
import com.souche.elasticsearch.query.AbstractEsQueryElement;
import com.souche.elasticsearch.query.EsQueryElement;
import java.util.Map;

public class EsTerm extends AbstractEsQueryElement<EsTerm> {

    private EsQueryElement queryElement;

    public EsTerm(KeyValuePair pair) {
        this.queryElement = pair;
    }

    @Override
    public EsTerm bind(EsQueryElement queryElement) {
        this.queryElement = queryElement;
        return this;
    }

    @Override
    public String typeName() {
        return QueryElementType.TERM.getName();
    }

    @Override
    public Map<String, Object> toMap() {
        if(queryElement == null) {
            return super.toMap();
        }
        return queryElement.toMap();
    }
}
