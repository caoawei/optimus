package com.souche.elasticsearch.common;

import com.souche.elasticsearch.constant.EsComparisonOperatorType;

public class RangeItem {

    private EsComparisonOperatorType comparisonOperatorType;

    private Object value;

    public EsComparisonOperatorType getComparisonOperatorType() {
        return comparisonOperatorType;
    }

    public void setComparisonOperatorType(EsComparisonOperatorType comparisonOperatorType) {
        this.comparisonOperatorType = comparisonOperatorType;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
