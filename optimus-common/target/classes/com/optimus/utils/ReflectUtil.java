package com.optimus.utils;

import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 反射工具类
 * @author caoawei
 * Created by on 2018/4/27.
 */
public class ReflectUtil {

    public static void makeAccessible(Field field){
        Assert.notNull(field,"参数不能为null");
        ReflectionUtils.makeAccessible(field);
    }

    public static void makeAccessible(Method method){
        Assert.notNull(method,"参数不能为null");
        ReflectionUtils.makeAccessible(method);
    }

    public static void makeAccessible(Constructor<?> constructor){
        Assert.notNull(constructor,"参数不能为null");
        ReflectionUtils.makeAccessible(constructor);
    }

    public static Object getField(Field field,Object src){
        return ReflectUtil.getField(field,src);
    }



    public static Class[] getActualParameterizedType(Class<?> cl) {
        if(cl == null){
            throw new NullPointerException("The cl parameter don't be null");
        }
        if(cl.isInterface()){
            return parameterizedTypeWithingInterface(cl);
        }
        Type type = cl.getGenericSuperclass();
        if(!isParameterizedType(type)){
            throw new IllegalArgumentException("参数cl非参数化类型");
        }

        ParameterizedType parameterizedType = (ParameterizedType) type;
        Type[] types = parameterizedType.getActualTypeArguments();
        Class[] rs = new Class[types.length];
        for (int i = 0;i < types.length;i++){
            rs[0] = (Class) types[i];
        }
        return rs;
    }

    private static Class[] parameterizedTypeWithingInterface(Class<?> cl) {
        Type[] type = cl.getGenericInterfaces();
        if(!isParameterizedType(type[0])){
            throw new IllegalArgumentException("参数cl非参数化类型");
        }

        ParameterizedType parameterizedType = (ParameterizedType) type[0];
        Type[] types = parameterizedType.getActualTypeArguments();
        Class[] rs = new Class[types.length];
        for (int i = 0;i < types.length;i++){
            rs[0] = (Class) types[i];
        }
        return rs;
    }

    public static boolean isParameterizedType(Type type){
        Assert.notNull(type,"参数不能为Null");
        return type instanceof ParameterizedType;
    }

    public static void main(String[] args){

    }

}
