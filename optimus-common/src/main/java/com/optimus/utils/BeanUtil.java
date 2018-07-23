package com.optimus.utils;

import com.optimus.common.exception.BizException;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author caoawei
 * Created by on 2018/4/28.
 */
public class BeanUtil {

    public static Map<String,Object> beanToMap(Object src) {
        Assert.notNull(src,"参数不能为null");
        Map<String,Object> rs = new LinkedHashMap<>();
        try {
            Field[] fields = src.getClass().getDeclaredFields();
            for (Field field : fields) {
                ReflectUtil.makeAccessible(field);
                Object val = ReflectUtil.getField(field,src);
                if (val != null) {
                    rs.put(field.getName(),val);
                }
            }
            return rs;
        } catch (Exception e) {
            throw new BizException("bean转map失败",e);
        }
    }

    public static <T> T mapToBean(Map<String,Object> map,Class<T> cl) {
        return null;
    }


}
