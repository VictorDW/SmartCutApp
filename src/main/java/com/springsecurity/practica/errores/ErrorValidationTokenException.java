package com.springsecurity.practica.errores;

public class ErrorValidationTokenException extends RuntimeException{
  public ErrorValidationTokenException(String message) {
    super(message);
  }
}
