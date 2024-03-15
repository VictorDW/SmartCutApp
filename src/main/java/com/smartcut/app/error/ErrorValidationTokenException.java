package com.smartcut.app.error;

import org.springframework.security.core.AuthenticationException;


public class ErrorValidationTokenException extends AuthenticationException {
  public ErrorValidationTokenException(String message) {
    super(message);
  }
}
