package com.souche.validation.core.exception;

public class NumberCheckException extends CheckException {

    public NumberCheckException(String message) {
        super(message);
    }

    public NumberCheckException(String message,Throwable e) {
        super(message,e);
    }
}
