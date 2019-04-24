package com.souche.validation.core.exception;

public class CheckException extends RuntimeException {

    private String message;

    public CheckException(String message) {
        super(message);
        this.message = message;
    }

    public CheckException(String message,Throwable e) {
        super(message,e);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
