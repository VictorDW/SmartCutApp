package com.smartcut.app.Error;

public class WithoutPermitsException extends RuntimeException{
    public WithoutPermitsException(String message) {
        super(message);
    }
}
