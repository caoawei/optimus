package com.optimus.common.exception;

public class BizException extends RuntimeException {

    private Integer code;

    public BizException(String errorMsg,Exception ex){
        super(errorMsg,ex);
    }

    public BizException(ExceptionCode exceptionCode){
        super(exceptionCode.getErrorMsg());
        this.code = exceptionCode.getCode();
    }

    public BizException(String errorMsg){
        super(errorMsg);
    }

    public BizException(Exception ex){
        super(ex);
    }

    public BizException(){
        super();
    }

    public Integer getCode() {
        return code;
    }
}
