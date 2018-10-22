package com.souche.elasticsearch.query.support;

import com.souche.elasticsearch.constant.QueryElementType;
import com.souche.elasticsearch.query.AbstractEsQueryElement;
import com.souche.elasticsearch.query.EsQueryElement;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * constant_score 查询类型,不计算_score相关度,可提高查询效率
 * @author caoawei
 */
public class EsConstantScore extends AbstractEsQueryElement<EsConstantScore> {

    private EsQueryElement queryElement;

    @Override
    public EsConstantScore bind(EsQueryElement queryElement) {
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
            rs.put(this.queryElement.typeName(),this.queryElement.toMap());
            return rs;
        }

    }

    @Override
    public String typeName() {
        return QueryElementType.CONSTANT_SCORE.getName();
    }
}
