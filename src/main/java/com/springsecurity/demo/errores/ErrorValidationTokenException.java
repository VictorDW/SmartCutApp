package com.springsecurity.demo.errores;

public class ErrorValidationTokenException extends RuntimeException{
  public ErrorValidationTokenException(String message) {
    super(message);
  }
}
