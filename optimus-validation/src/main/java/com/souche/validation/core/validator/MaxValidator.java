package com.souche.validation.core.validator;

import com.souche.validation.core.annotation.Max;
import java.lang.reflect.Field;

public class MaxValidator extends AbstractNumberValidator<Max> {

    @Override
    public Class<Max> supportAnnotation() {
        return Max.class;
    }

    @Override
    protected void doValidate(Object source, Max annotation) {
        checkMax(annotation.value(),source,annotation.errorMsg());
    }

    @Override
    protected void doValidate(Object source, Field field, Object fieldValue, Max annotation) {
        checkMax(annotation.value(),fieldValue,annotation.errorMsg());
    }
}
