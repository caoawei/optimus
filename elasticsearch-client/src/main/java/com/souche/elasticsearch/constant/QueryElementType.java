package com.souche.elasticsearch.constant;

/**
 * 查询元素类型
 * @author caoawei
 */
public enum QueryElementType {

    /**
     * 空,位置类型
     */
    EMPTY(""),

    /**
     * 查询
     */
    QUERY("query"),

    /**
     * term 精确查询(包含操作,非绝对精确,精确操作需带上("tag_count":number))
     */
    TERM("term"),

    /**
     * terms 多个精确值(包含操作,非绝对精确,通term)
     */
    TERMS("terms"),

    /**
     * bool 过滤器(用于组合不同的过滤器)
     */
    BOOL("bool"),

    /**
     * 过滤器
     */
    FILTER("filter"),

    /**
     * filtered (与query结合用)
     */
    FILTERED("filtered"),

    /**
     * constant_score 不计算score(相关度,以常量1返回)
     */
    CONSTANT_SCORE("constant_score"),

    /**
     * 范围查询 range
     */
    RANGE("range"),

    /**
     * 前缀查询
     */
    PREFIX("prefix"),

    /**
     * 通配符查询 ?, *
     */
    WILDCARD("wildcard"),

    /**
     * 正则查询
     */
    REGEXP("regexp"),

    /**
     * 匹配查询(模糊)
     */
    MATCH("match"),

    /**
     * 短语匹配查询
     */
    MATCH_PARASE("match_phrase")

    ;

    String name;

    QueryElementType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
