package com.souche.validation.core.validator;

import com.souche.validation.core.Validator;
import com.souche.validation.core.exception.NullCheckException;
import com.souche.validation.core.util.ValidationUtils;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import org.springframework.util.ReflectionUtils;

public abstract class AbstractValidator<T extends Annotation> implements Validator<T> {

    @Override
    public void validate(Object source, T annotation) {
        doValidate(source,annotation);
    }

    @Override
    public void validate(Object source, Field field, T annotation) {
        checkNull(source);
        ReflectionUtils.makeAccessible(field);
        Object value = ReflectionUtils.getField(field,source);
        doValidate(source,field,value,annotation);
    }

    protected final void checkNull(Object source) {
        checkNull(source,null);
    }

    protected final void checkNull(Object source,Field field,Object fieldValue) {
        checkNull(source,field,fieldValue,null);
    }

    protected final void checkNull(Object source,String errorMsg) {
        if(source == null) {
            throw new NullCheckException(ValidationUtils.isEmpty(errorMsg) ? "Method Parameter not be null" : errorMsg);
        }
    }

    protected final void checkNull(Object source,Field field,Object fieldValue,String errorMsg) {
        if(fieldValue == null) {
            throw new NullCheckException(ValidationUtils.isEmpty(errorMsg) ? String.format("Method Parameter field %s.%s not be null",source.getClass().getName(),field.getName()) : errorMsg);
        }
    }

    /**
     * 执行验证逻辑
     * @param source 待验证对象(方法参数)
     * @param annotation source上的注解
     */
    protected abstract void doValidate(Object source, T annotation);

    /**
     * 执行验证逻辑
     * @param source 待验证field所属对象(方法参数)
     * @param field 待验证对象属性
     * @param fieldValue 待验证对象属性值
     * @param annotation field上的注解
     */
    protected abstract void doValidate(Object source, Field field,Object fieldValue, T annotation);
}
