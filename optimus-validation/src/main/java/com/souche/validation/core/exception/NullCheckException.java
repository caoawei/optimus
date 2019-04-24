package com.souche.validation.core.exception;

public class NullCheckException extends CheckException {

    public NullCheckException(String message) {
        super(message);
    }

    public NullCheckException(String message,Throwable e) {
        super(message,e);
    }
}
