package com.souche.validation.core.validator;

import com.souche.validation.core.annotation.Text;
import com.souche.validation.core.exception.DataTypeIllegalCheckException;
import com.souche.validation.core.exception.TextCheckException;
import com.souche.validation.core.util.ValidationUtils;
import java.lang.reflect.Field;

public class TextValidator extends AbstractValidator<Text> {

    @Override
    public Class<Text> supportAnnotation() {
        return Text.class;
    }

    @Override
    protected void doValidate(Object source, Text annotation) {
        checkNull(source,annotation.errorMsg());
        if(!(source instanceof CharSequence)) {
            throw new DataTypeIllegalCheckException(String.format("%s must be annotated on CharSequence or its subclass type",supportAnnotation().getName()));
        }

        CharSequence cs = (CharSequence) source;

        if(annotation.minLen() > 0 && cs.length() < annotation.minLen()) {
            throw new TextCheckException(ValidationUtils.isEmpty(annotation.errorMsg()) ? String.format("the value length cannot less than %f",annotation.minLen()) : annotation.errorMsg());
        }

        if(annotation.maxLen() > 0 && cs.length() > annotation.maxLen()) {
            throw new TextCheckException(ValidationUtils.isEmpty(annotation.errorMsg()) ? String.format("the value length cannot greater than %f",annotation.minLen()) : annotation.errorMsg());
        }
    }

    @Override
    protected void doValidate(Object source, Field field, Object fieldValue, Text annotation) {
        checkNull(source,field,fieldValue);
        doValidate(fieldValue,annotation);
    }

}
