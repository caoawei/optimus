package com.souche.elasticsearch.query;

import com.souche.elasticsearch.common.RangeItem;
import com.souche.elasticsearch.query.support.EsBool;
import com.souche.elasticsearch.query.support.EsConstantScore;
import com.souche.elasticsearch.query.support.EsFilter;
import com.souche.elasticsearch.query.support.EsFiltered;
import com.souche.elasticsearch.query.support.EsQuery;
import com.souche.elasticsearch.query.support.EsRange;
import com.souche.elasticsearch.query.support.EsSearch;
import com.souche.elasticsearch.query.support.EsTerm;
import com.souche.elasticsearch.query.support.EsTerms;
import com.souche.elasticsearch.query.support.KeyValuePair;
import com.souche.elasticsearch.query.support.RangeKeyValuePair;

/**
 * Es构造器
 * @author caoawei
 */
public class EsBuilder {

    /**
     * 构建es 查询对象
     * @param queryElement
     * @return
     */
    public static EsSearch search(EsQueryElement queryElement) {
        return new EsSearch().bind(queryElement);
    }

    /**
     * 构造 query 项(顶层)
     * @return query
     */
    public static EsQuery query() {
        return new EsQuery();
    }

    /**
     * 构造 constant_score(不计算score相关度,默认返回1)
     * @return constant_score
     */
    public static EsConstantScore constantScore() {
        return new EsConstantScore();
    }
    /**
     * 构造 filtered(用于与query结合)
     * @return filtered
     */
    public static EsFiltered filtered() {
        return new EsFiltered();
    }

    /**
     * 构造 filter查询项
     * @return filter
     */
    public static EsFilter filter() {
        return new EsFilter();
    }

    /**
     * 构造 bool查询类型(通常用于组合不同的过滤器)
     * @return bool
     */
    public static EsBool bool() {
        return new EsBool();
    }

    /**
     * 构造 term查询类型
     * @param pair 键值对
     * @return term
     */
    public static EsTerm term(KeyValuePair pair) {
        return new EsTerm(pair);
    }

    /**
     * 构造 terms 查询类型
     * @param pair 键值对
     * @return terms
     */
    public static EsTerms terms(KeyValuePair pair) {
        return new EsTerms().bind(pair);
    }

    public static EsRange range() {
        return new EsRange();
    }

    /**
     * 构造键值对(表示实际的查询项)
     * @param field 键
     * @param value 值
     * @return 键值对
     */
    public static KeyValuePair keyValuePair(String field, Object value) {
        return KeyValuePair.create(field,value);
    }

    /**
     * 构造 键值对(适用于terms类型的查询)
     *
     * 注释事项:
     * 如果调用此方法,第二个参数只传递一个参数(非数组类型),
     * 则会选择调用{@link #keyValuePair(String, Object)},两者生成的查询json是不同的.
     *
     * 如果项调用此类型，而又只有一个值的话，需要调用者显示的传递数组过来.
     * eg: keyValuePair(field,new Object[]{vlaue})
     *
     * @param field 键
     * @param values 值
     * @return 键值对
     */
    public static KeyValuePair keyValuePair(String field, Object... values) {
        return KeyValuePair.create(field,values);
    }

    /**
     * 构建 range 项
     * @param field field
     * @param items 匹配条件
     * @return range
     */
    public static RangeKeyValuePair rangeKeyValuePair(String field, RangeItem... items) {
        RangeKeyValuePair rangeKeyValuePair = new RangeKeyValuePair(field);
        if(items != null && items.length > 0) {
            for (RangeItem item : items) {
                rangeKeyValuePair.setItem(item.getComparisonOperatorType(),item.getValue());
            }
        }
        return rangeKeyValuePair;
    }
}
