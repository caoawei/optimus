package com.souche.elasticsearch.query.support;

import com.souche.elasticsearch.constant.QueryElementType;
import com.souche.elasticsearch.query.AbstractEsQueryElement;
import com.souche.elasticsearch.query.EsQueryElement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 布尔过滤器
 * @author caoawei
 */
public class EsBool extends AbstractEsQueryElement<EsBool> {

    /**
     * must 集合(类似于 and)
     */
    private List<EsQueryElement> must;

    /**
     * must_not 集合(类似于 not)
     */
    private List<EsQueryElement> mustNot;

    /**
     * should 集合(类似于 or)
     */
    private List<EsQueryElement> should;

    public EsBool() {
        this.must = new ArrayList<>(2);
        this.mustNot = new ArrayList<>(2);
        this.should = new ArrayList<>(2);
    }

    /**
     * 设置must
     * @param element 查询元素
     * @return bool
     */
    public EsBool must(EsQueryElement element) {
        this.must.add(element);
        return this;
    }

    /**
     * 设置should
     * @param element 查询元素
     * @return bool
     */
    public EsBool should(EsQueryElement element) {
        this.should.add(element);
        return this;
    }

    /**
     * 设置mustNot
     * @param element 查询元素
     * @return bool
     */
    public EsBool mustNot(EsQueryElement element) {
        this.mustNot.add(element);
        return this;
    }

    @Override
    public EsBool bind(EsQueryElement queryElement) {
        return this;
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String,Object> map = new LinkedHashMap<>(4);
        convert2Map(must,"must",map);
        convert2Map(should,"should",map);
        convert2Map(mustNot,"must_not",map);
        return map;
    }

    @Override
    public String typeName() {
        return QueryElementType.BOOL.getName();
    }

    private static void convert2Map(List<EsQueryElement> items, String field, Map<String,Object> container) {
        int size = items.size();
        if(size < 1) {
            return;
        }

        List<Map<String,Object>> mapList = new ArrayList<>(size);
        container.put(field,mapList);
        if(size > 0) {
            for (EsQueryElement element : items) {
                Map<String,Object> item = new LinkedHashMap<>(1);
                if(QueryElementType.EMPTY.getName().equals(element.typeName())) {
                    mapList.add(element.toMap());
                } else {
                    item.put(element.typeName(),element.toMap());
                    mapList.add(item);
                }
            }
        }
    }
}
