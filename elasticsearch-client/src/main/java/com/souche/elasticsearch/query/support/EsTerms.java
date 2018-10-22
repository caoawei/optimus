package com.souche.elasticsearch.query.support;

import com.souche.elasticsearch.constant.QueryElementType;
import com.souche.elasticsearch.query.AbstractEsQueryElement;
import com.souche.elasticsearch.query.EsQueryElement;
import java.util.Map;

public class EsTerms extends AbstractEsQueryElement<EsTerms> {

    private EsQueryElement queryElement;

    @Override
    public String typeName() {
        return QueryElementType.TERMS.getName();
    }

    @Override
    public EsTerms bind(EsQueryElement queryElement) {
        this.queryElement = queryElement;
        return this;
    }

    @Override
    public Map<String, Object> toMap() {
        if(queryElement == null) {
            return super.toMap();
        }
        return queryElement.toMap();
    }
}
