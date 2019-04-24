package com.souche.validation.core.validator;

import com.souche.validation.core.annotation.Number;
import java.lang.reflect.Field;

public class NumberValidator extends AbstractNumberValidator<Number> {

    @Override
    public Class<Number> supportAnnotation() {
        return Number.class;
    }

    @Override
    protected void doValidate(Object source, Number annotation) {
        if(annotation.min() != Double.MIN_VALUE) {
            checkMin(annotation.min(),source,annotation.errorMsg());
        }
        if(annotation.max() != Double.MAX_VALUE) {
            checkMax(annotation.max(),source,annotation.errorMsg());
        }
    }

    @Override
    protected void doValidate(Object source, Field field, Object fieldValue, Number annotation) {
        checkNull(source,field,fieldValue);
        doValidate(fieldValue,annotation);
    }

}
