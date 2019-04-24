package com.souche.validation.core.validator;

import com.souche.validation.core.annotation.NonEmpty;
import com.souche.validation.core.exception.EmptyCheckException;
import com.souche.validation.core.util.ValidationUtils;
import java.lang.reflect.Field;

public class NonEmptyValidator extends AbstractValidator<NonEmpty> {


    @Override
    public Class<NonEmpty> supportAnnotation() {
        return NonEmpty.class;
    }

    @Override
    protected void doValidate(Object source, NonEmpty annotation) {
        checkNull(source);
        if(ValidationUtils.isEmpty(source)) {
            throw new EmptyCheckException(String.format("Method parameter %s cannot be empty",source.getClass().getName()));
        }
    }

    @Override
    protected void doValidate(Object source, Field field, Object fieldValue, NonEmpty annotation) {
        checkNull(source,field,fieldValue);
        if(ValidationUtils.isEmpty(fieldValue)) {
            throw new EmptyCheckException(String.format("Method parameter field %s.%s cannot be empty",source.getClass().getName(),field.getName()));
        }
    }
}
