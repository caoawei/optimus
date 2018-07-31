package com.optimus.utils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.bind.TypeAdapters;
import com.optimus.common.exception.BizException;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Administrator on 2018/3/22.
 */
public class Utils {

    private static final String BLANK = "";
    private static final GsonBuilder GSON_BUILDER;
    private static final String DEFALUT_JSON_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    static {
        GSON_BUILDER = new Gson().newBuilder();
        GSON_BUILDER.setDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public static boolean isEmpty(String input){
        return input == null || input.matches("\\s*") || input.trim().equals("null");
    }

    public static boolean isEmpty(Collection input){
        return input == null || input.isEmpty();
    }

    public static boolean isEmpty(Map input){
        return input == null || input.isEmpty();
    }

    public static boolean isNotEmpty(String input){
        return !isEmpty(input);
    }

    public static boolean isNotEmpty(Collection input){
        return !isEmpty(input);
    }

    public static boolean isNotEmpty(Map input){
        return !isEmpty(input);
    }

    public static boolean isNull(String input){
        return input == null || isEmpty(input);
    }

    public static boolean isNull(Collection input){
        return input == null;
    }

    public static boolean isNull(Map input){
        return input == null;
    }

    public static boolean isNull(Object input){
        return input == null;
    }

    public static <T> String join(Collection<T> list,String separator){
        if(isEmpty(list)){
            return BLANK;
        }
        StringBuilder sb = new StringBuilder();
        Iterator<T> iter = list.iterator();
        while (iter.hasNext()) {
            sb.append(iter.next());
            if (iter.hasNext())
                sb.append(separator);
        }
        return sb.toString();
    }

    public static void setFieldValue(String fieldName,Object src,Object val){
        if(isNull(src)){
            throw new NullPointerException("src can not be null");
        }
        if(isEmpty(fieldName)){
            throw new IllegalArgumentException("fieldName is Illegal");
        }

        try {
            Field field = src.getClass().getDeclaredField(fieldName);
            if(field != null){
                ReflectionUtils.makeAccessible(field);
                ReflectionUtils.setField(field,src,val);
            }
        }catch (Exception e){
            throw new BizException(e);
        }
    }

    public static Object getFieldValue(String fieldName,Object src){
        if(isNull(src)){
            throw new NullPointerException("src can not be null");
        }
        if(isEmpty(fieldName)){
            throw new IllegalArgumentException("fieldName is Illegal");
        }

        try {
            Field field = src.getClass().getDeclaredField(fieldName);
            if(field != null){
                ReflectionUtils.makeAccessible(field);
                return ReflectionUtils.getField(field,src);
            }

            return null;
        }catch (Exception e){
            throw new BizException(e);
        }
    }

    public static String toJson(Object input){
        Gson gson = createGson();
        return gson.toJson(input);
    }

    public static <T> T fromJson(String input,Class<T> type) {
        Gson gson = createGson();
        return gson.fromJson(input,type);
    }

    public static List<String> string2List(String str,String delimit,boolean isTrime) {
        if(str == null) {
            throw new NullPointerException("the str is null");
        }

        StringTokenizer stringTokenizer = new StringTokenizer(str,delimit);
        List<String> rs = new ArrayList<>(stringTokenizer.countTokens());
        while (stringTokenizer.hasMoreTokens()) {
            String token = stringTokenizer.nextToken();
            if(isTrime) {
                rs.add(token.trim());
            } else {
                rs.add(token);
            }
        }
        return rs;
    }

    private static final Gson createGson() {
        return GSON_BUILDER.create();
    }
}
