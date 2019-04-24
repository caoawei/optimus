package com.souche.validation.core.util;

import com.souche.validation.core.annotation.Max;
import com.souche.validation.core.annotation.Min;
import com.souche.validation.core.annotation.NonEmpty;
import com.souche.validation.core.annotation.NonNull;
import com.souche.validation.core.annotation.Number;
import com.souche.validation.core.annotation.Text;
import com.souche.validation.core.annotation.Validation;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class ValidationUtils {

    private static final Pattern STRING_EMPTY = Pattern.compile("\\s*");

    private static List<Class> SUPPORT_ANNOTATION;

    static {
        SUPPORT_ANNOTATION = new ArrayList<>(4);
        SUPPORT_ANNOTATION.add(Min.class);
        SUPPORT_ANNOTATION.add(Max.class);
        SUPPORT_ANNOTATION.add(NonNull.class);
        SUPPORT_ANNOTATION.add(NonEmpty.class);
        SUPPORT_ANNOTATION.add(Number.class);
        SUPPORT_ANNOTATION.add(Text.class);
    }

    public static boolean isNumber(Class type) {
        return type != null &&
                (byte.class == type
                || short.class == type
                || int.class == type
                || long.class == type
                || float.class == type
                || double.class == type
                || java.lang.Number.class.isAssignableFrom(type));
    }

    public static java.lang.Number getNumber(Object source) {
        if(source == null) {
            return null;
        }

        Class tc = source.getClass();
        if(byte.class == tc
                || short.class == tc
                || int.class == tc
                || long.class == tc
                || float.class == tc
                || double.class == tc) {
            return BigDecimal.valueOf((double) source);
        } else if(source instanceof java.lang.Number) {
            return (BigDecimal) source;
        } else {
            return null;
        }

    }

    public static void determineValidationAnnotation(Class classType,Method method) {
        Class[] parameterTypes = method.getParameterTypes();
        if(parameterTypes == null || parameterTypes.length < 1) {
            return;
        }

        String ruleName = null;
        for (Class parameterType : parameterTypes) {

        }
    }

    public static boolean isEmpty(String str) {
        return str == null || STRING_EMPTY.matcher(str).matches();
    }

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isEmpty(Map<?,?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isEmpty(Object obj) {
        if(obj == null) {
            return true;
        }

        if(obj instanceof String) {
            return isEmpty((String)obj);
        } else if(obj instanceof Collection) {
            return isEmpty((Collection)obj);
        } else if(obj instanceof Map) {
            return isEmpty((Map)obj);
        } else {
            return false;
        }
    }

    public static boolean isSupport(Annotation annotation) {
        return SUPPORT_ANNOTATION.contains(annotation.annotationType()) || annotation.getClass().isAnnotationPresent(Validation.class);
    }

    public static boolean needCheck(Class<?> type) {
        return type != Object.class
                && !type.isPrimitive()
                && !type.getName().startsWith("java")
                && !type.getName().startsWith("javax")
                && type.getClassLoader() != null;
    }

    public static int checkNumber(Object val,double standardValue,int type) {
        if(ValidationUtils.isNumber(val.getClass())) {
            java.lang.Number number = ValidationUtils.getNumber(val);
            if(number == null) {
                return -1;
            }

            double v = number.doubleValue();
            if(type == 1 && v > standardValue) {
                return 0;
            } else if(type == 2 && v < standardValue) {
                return 0;
            }

            return 1;
        }

        return -1;
    }
}
