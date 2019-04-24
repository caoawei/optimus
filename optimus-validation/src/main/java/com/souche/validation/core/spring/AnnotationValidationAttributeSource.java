package com.souche.validation.core.spring;

import com.souche.validation.core.RuleDefinition;
import com.souche.validation.core.ValidationAttribute;
import com.souche.validation.core.ValidationAttributeSource;
import com.souche.validation.core.common.DefaultValidationAttribute;
import com.souche.validation.core.common.RuleDefinitionFactory;
import com.souche.validation.core.util.ValidationUtils;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.MethodClassKey;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

public class AnnotationValidationAttributeSource implements ValidationAttributeSource {

    /** Method mapping to ValidationAttribute **/
    private Map<Object,ValidationAttribute> attributeCache = new ConcurrentHashMap<>();

    /** Helping get method parameter name */
    private ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

    private boolean allowOnlyPublicMethod = false;

    @Override
    public ValidationAttribute getValidationAttribute(Method method, Class targetClass) {
        if(!"com.optimus.module.account.service.TestService".equals(targetClass.getName())) {
            return null;
        }

        int count = method.getParameterCount();
        if(count < 1) {
            return null;
        }

        if (method.getDeclaringClass() == Object.class) {
            return null;
        }

        // First, see if we have a cached value.
        Object cacheKey = getCacheKey(method, targetClass);
        ValidationAttribute cached = this.attributeCache.get(cacheKey);

        if (cached != null) {
            return cached;
        }

        ValidationAttribute vt = computeValidationAttribute(method,targetClass);
        if(vt != null) {
            this.attributeCache.put(cacheKey,vt);
        }

        return vt;
    }

    public boolean isAllowNonPublicMethod() {
        return allowOnlyPublicMethod;
    }

    public void setAllowNonPublicMethod(boolean allowNonPublicMethod) {
        this.allowOnlyPublicMethod = allowNonPublicMethod;
    }

    private Object getCacheKey(Method method, @Nullable Class<?> targetClass) {
        return new MethodClassKey(method, targetClass);
    }

    private ValidationAttribute computeValidationAttribute(Method method,Class<?> targetClass) {

        // Not allow non public method,if any
        if(isAllowNonPublicMethod() && !Modifier.isPublic(method.getModifiers())) {
            return null;
        }

        Method specificMethod = AopUtils.getMostSpecificMethod(method, targetClass);

        ValidationAttribute va = findValidationAttribute(specificMethod,targetClass);

        if(va != null) {
            return va;
        }

        Class<?> specificClass = ClassUtils.getUserClass(targetClass);

        // 2. Find annotation from its interfaces.
        Class<?>[] ifs = specificClass.getInterfaces();
        for (Class<?> i : ifs) {
            va = findValidationAttribute(ReflectionUtils.findMethod(i,method.getName(),method.getParameterTypes()),targetClass);
            if(va != null) {
                return va;
            }
        }

        // 3. Find annotation from its superclass.
        Class<?> superClass = specificClass.getSuperclass();
        if(superClass != Object.class) {
            return findValidationAttribute(ReflectionUtils.findMethod(superClass,method.getName(),method.getParameterTypes()),targetClass);
        }

        return null;
    }

    private ValidationAttribute findValidationAttribute(Method method,Class<?> targetClass) {
        if(method == null) {
            return null;
        }

        Class<?>[] parameterTypes = method.getParameterTypes();
        if(parameterTypes.length < 1) {
            return null;
        }

        Annotation[][] annotations = method.getParameterAnnotations();

        DefaultValidationAttribute defaultValidationAttribute = null;

        String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);

        // 按照方法参数列表解析其上的注解
        for (int i = 0; i < parameterTypes.length;i++) {

            // First handle annotation on method parameters
            defaultValidationAttribute = handleAnnotationOnMethodParameter(method, targetClass, parameterTypes[i], parameterNames == null ? "" : parameterNames[i], annotations[i], defaultValidationAttribute, i);

            // Second handle inner field for parameter,will not cascade
            defaultValidationAttribute = handleAnnotationOnMethodParameterField(method, targetClass, parameterTypes[i], defaultValidationAttribute,i);
        }

        return defaultValidationAttribute;
    }

    private static DefaultValidationAttribute handleAnnotationOnMethodParameterField(Method method, Class<?> targetClass, Class<?> parameterType, DefaultValidationAttribute defaultValidationAttribute,int index) {

        if(!ValidationUtils.needCheck(parameterType)) {
            return defaultValidationAttribute;
        }

        Field[] fields = parameterType.getDeclaredFields();
        if(fields == null || fields.length < 1) {
            return defaultValidationAttribute;
        }

        for (Field field : fields) {
            Annotation[] fundAnnotations = AnnotationUtils.getAnnotations(field);
            if(fundAnnotations == null || fundAnnotations.length < 1) {
                continue;
            }

            List<Annotation> existsAnn = new ArrayList<>();

            filterAnnotation(existsAnn, fundAnnotations);

            ReflectionUtils.makeAccessible(field);
            if(existsAnn.size() > 0) {
                if(defaultValidationAttribute == null) {
                    defaultValidationAttribute = new DefaultValidationAttribute(method,targetClass);
                }

                RuleDefinition ruleDefinition = RuleDefinitionFactory.createRuleDefinition(method,targetClass,index,field.getDeclaringClass(),2, field.getType(),field.getName(),existsAnn);
                defaultValidationAttribute.addRuleDefinition(parameterType,ruleDefinition);
            }
        }
        return defaultValidationAttribute;
    }

    private static DefaultValidationAttribute handleAnnotationOnMethodParameter(Method method, Class<?> targetClass, Class<?> parameterType,String parameterName, Annotation[] annotation, DefaultValidationAttribute defaultValidationAttribute, int index) {
        Annotation[] sa = annotation;

        // 方法参数注解上有注解
        if(sa != null && sa.length > 0) {
            List<Annotation> existsAnn = new ArrayList<>(sa.length);

            filterAnnotation(existsAnn, sa);

            if(existsAnn.size() > 0) {
                RuleDefinition ruleDefinition = RuleDefinitionFactory.createRuleDefinition(method,targetClass,index,null,1, parameterType,parameterName,existsAnn);
                if(defaultValidationAttribute == null) {
                    defaultValidationAttribute = new DefaultValidationAttribute(method,targetClass);
                }
                defaultValidationAttribute.addRuleDefinition(index,ruleDefinition);
            }
        }

        return defaultValidationAttribute;
    }

    private static void filterAnnotation(List<Annotation> existsAnn, Annotation[] fundAnnotations) {
        for (Annotation a : fundAnnotations) {
            if (ValidationUtils.isSupport(a)) {
                existsAnn.add(a);
            }
        }
    }

}
