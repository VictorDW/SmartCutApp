package com.smartcut.app.error;

public class WithoutPermitsException extends RuntimeException{
    public WithoutPermitsException(String message) {
        super(message);
    }
}
