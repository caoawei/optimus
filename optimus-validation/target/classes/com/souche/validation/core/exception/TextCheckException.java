package com.souche.validation.core.exception;

public class TextCheckException extends CheckException {

    public TextCheckException(String message) {
        super(message);
    }

    public TextCheckException(String message,Throwable e) {
        super(message,e);
    }
}
