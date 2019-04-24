package com.souche.validation.core.exception;

public class DataTypeIllegalCheckException extends CheckException {

    public DataTypeIllegalCheckException(String message) {
        super(message);
    }

    public DataTypeIllegalCheckException(String message,Throwable e) {
        super(message,e);
    }
}
