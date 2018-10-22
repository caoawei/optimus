package com.souche.elasticsearch.query.support;

import com.souche.elasticsearch.constant.EsComparisonOperatorType;
import com.souche.elasticsearch.query.AbstractEsQueryElement;
import com.souche.elasticsearch.query.EsQueryElement;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * 用法:
 *
 * <code>
 *     RangeKeyValuePair range = new RangeKeyValuePair() // new RangeKeyValuePair(field)
 *     // 范围查询条件:    field > value1  and  field < value2
 *     // 也可以只设置其中一项
 *     // 可写成链式调用: range.setItem(p1,p2).setItem(p3,p4);
 *
 *     // field > value1
 *     range.setItem(EsComparisonOperatorType.GT,value1);
 *
 *     // field < value2
 *     range.setItem(EsComparisonOperatorType.LT,value2);
 *
 *
 *     // field >= value3
 *     //range.setItem(EsComparisonOperatorType.GTE,value3);
 *
 *     // field <= value4
 *     //range.setItem(EsComparisonOperatorType.LTE,value4);
 * </code>
 * @author caoawei
 */
public class RangeKeyValuePair extends AbstractEsQueryElement<RangeKeyValuePair> {

    private String field;

    private Map<String,Object> value;

    public RangeKeyValuePair() {
        value = new LinkedHashMap<>(4);
    }

    public RangeKeyValuePair(String field) {
        this();
        this.field = field;
    }

    /**
     * 设置field
     * @param field field
     * @return range
     */
    public RangeKeyValuePair setField(String field) {
        this.field = field;
        return this;
    }

    /**
     * 设置范围查询项
     * @param comparisonOperatorType 比较运算符
     * @param value 操作数
     * @return range
     */
    public RangeKeyValuePair setItem(EsComparisonOperatorType comparisonOperatorType, Object value) {
        this.value.put(comparisonOperatorType.getName(),value);
        return this;
    }

    @Override
    public RangeKeyValuePair bind(EsQueryElement queryElement) {
        return this;
    }

    @Override
    public Map<String, Object> toMap() {
        if(this.field == null) {
            return super.toMap();
        }

        Map<String,Object> rs = new LinkedHashMap<>(1);
        rs.put(field,value);
        return rs;
    }

}
