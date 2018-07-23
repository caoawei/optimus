package com.optimus.common.exception;

public enum ExceptionCode {

    ;
    String errorMsg;

    Integer code;

    ExceptionCode(String errorMsg,Integer code){
        this.errorMsg = errorMsg;
        this.code = code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public Integer getCode() {
        return code;
    }
}
