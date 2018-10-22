package com.souche.elasticsearch.query.support;

import com.souche.elasticsearch.query.AbstractEsQueryElement;
import com.souche.elasticsearch.query.EsQueryElement;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 键值对(基础对象)
 * (注: 不允许嵌套)
 * @author caoawei
 */
public class KeyValuePair extends AbstractEsQueryElement {

    private String field;

    private Object value;

    /**
     * 创建键值对象
     * 格式: {field:value}
     * @param field 键
     * @param value 值
     * @return 键值对象
     */
    public static KeyValuePair create(String field,Object value) {
        KeyValuePair pair = new KeyValuePair();
        pair.field = field;
        pair.value = value;
        return pair;
    }

    /**
     * 创建键值对象
     * 格式: {field:[value1,value2,...]}
     * @param field 键
     * @param values 值
     * @return 键值对象
     */
    public static KeyValuePair create(String field,Object... values) {
        KeyValuePair pair = new KeyValuePair();
        pair.field = field;
        pair.value = values;
        return pair;
    }

    @Override
    public KeyValuePair bind(EsQueryElement queryElement) {
        return this;
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String,Object> rs = new LinkedHashMap<>(1);
        rs.put(field,value);
        return rs;
    }
}
