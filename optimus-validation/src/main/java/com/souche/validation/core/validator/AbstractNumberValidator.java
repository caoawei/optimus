package com.souche.validation.core.validator;

import com.souche.validation.core.exception.DataTypeIllegalCheckException;
import com.souche.validation.core.exception.NumberCheckException;
import com.souche.validation.core.util.ValidationUtils;
import java.lang.annotation.Annotation;

public abstract class AbstractNumberValidator<T extends Annotation> extends AbstractValidator<T> {

    /**
     * 检查数字类型下界
     * @param standardValue 标准值
     * @param sourceValue 待检查值
     */
    protected final void checkMin(double standardValue,Object sourceValue,String configErrorMsg) {
        checkNull(sourceValue);
        checkNumber(sourceValue);
        Number val = ValidationUtils.getNumber(sourceValue);
        if(val.doubleValue() < standardValue) {
            throw new NumberCheckException(ValidationUtils.isEmpty(configErrorMsg) ? String.format("value cannot less than %f",standardValue) : configErrorMsg);
        }
    }

    /**
     * 检查数字类型下界
     * @param standardValue 标准值
     * @param sourceValue 待检查值
     */
    protected final void checkMax(double standardValue,Object sourceValue,String configErrorMsg) {
        checkNull(sourceValue);
        checkNumber(sourceValue);
        Number val = ValidationUtils.getNumber(sourceValue);
        if(val.doubleValue() > standardValue) {
            throw new NumberCheckException(ValidationUtils.isEmpty(configErrorMsg) ? String.format("value cannot greater than %f",standardValue) : configErrorMsg);
        }
    }

    /**
     * 是否是数值类型
     * @param sourceValue 待检测对象
     */
    private final void checkNumber(Object sourceValue) {
        if(!ValidationUtils.isNumber(sourceValue.getClass())) {
            throw new DataTypeIllegalCheckException(String.format("%s must be annotated on number type",supportAnnotation().getName()));
        }
    }

}
