package com.souche.elasticsearch.query.support;

import com.optimus.utils.Utils;
import com.souche.elasticsearch.query.AbstractEsQueryElement;
import com.souche.elasticsearch.query.EsQueryElement;
import java.util.LinkedHashMap;
import java.util.Map;

public class EsSearch extends AbstractEsQueryElement<EsSearch> {

    private EsQueryElement queryElement;

    public String toJson() {
        Map<String,Object> rs = new LinkedHashMap<>(1);
        rs.put(queryElement.typeName(),queryElement.toMap());
        return Utils.toJson(rs);
    }

    @Override
    public EsSearch bind(EsQueryElement queryElement) {
        this.queryElement = queryElement;
        return this;
    }

    @Override
    public Map<String, Object> toMap() {
        if(queryElement == null) {
            return super.toMap();
        }

        Map<String,Object> rs = new LinkedHashMap<>(1);
        rs.put(queryElement.typeName(),queryElement.toMap());
        return rs;
    }
}
