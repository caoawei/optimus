package com.souche.elasticsearch.constant;

/**
 * 比较运算符
 * @author caoawei
 */
public enum EsComparisonOperatorType {

    /**
     * 大于
     */
    GT("gt"),

    /**
     * 大于等于
     */
    GTE("gte"),

    /**
     * 小于
     */
    LT("lt"),

    /**
     * 小于等于
     */
    LTE("lte"),

    ;

    /**
     * 运算符名称
     */
    String name;

    EsComparisonOperatorType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
