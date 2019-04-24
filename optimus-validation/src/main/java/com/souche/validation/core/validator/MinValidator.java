package com.souche.validation.core.validator;

import com.souche.validation.core.annotation.Min;
import java.lang.reflect.Field;

public class MinValidator extends AbstractNumberValidator<Min> {

    @Override
    public Class<Min> supportAnnotation() {
        return Min.class;
    }

    @Override
    protected void doValidate(Object source, Min annotation) {
        checkMin(annotation.value(),source,annotation.errorMsg());
    }

    @Override
    protected void doValidate(Object source, Field field, Object fieldValue, Min annotation) {
        checkMin(annotation.value(),fieldValue,annotation.errorMsg());
    }
}
