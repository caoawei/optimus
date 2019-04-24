package com.souche.validation.core.validator;

import com.souche.validation.core.annotation.NonNull;
import java.lang.reflect.Field;

public class NonNullValidator extends AbstractValidator<NonNull> {

    @Override
    public Class<NonNull> supportAnnotation() {
        return NonNull.class;
    }

    @Override
    protected void doValidate(Object source, NonNull annotation) {
        checkNull(source,annotation.errorMsg());
    }

    @Override
    protected void doValidate(Object source, Field field, Object fieldValue, NonNull annotation) {
        checkNull(source,field,fieldValue,annotation.errorMsg());
    }

}
