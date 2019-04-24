package com.souche.validation.core.exception;

public class EmptyCheckException extends CheckException {

    public EmptyCheckException(String message) {
        super(message);
    }

    public EmptyCheckException(String message,Throwable e) {
        super(message,e);
    }
}
