package com.souche.elasticsearch.query.support;

import com.souche.elasticsearch.constant.QueryElementType;
import com.souche.elasticsearch.query.AbstractEsQueryElement;
import com.souche.elasticsearch.query.EsQueryElement;
import java.util.Map;

public class EsRange extends AbstractEsQueryElement<EsRange> {

    private EsQueryElement queryElement;

    @Override
    public EsRange bind(EsQueryElement queryElement) {
        this.queryElement = queryElement;
        return this;
    }

    @Override
    public Map<String, Object> toMap() {
        if(this.queryElement == null) {
            return super.toMap();
        }

        return this.queryElement.toMap();
    }

    @Override
    public String typeName() {
        return QueryElementType.RANGE.getName();
    }
}
