package com.smartcut.app.Error;

import org.springframework.security.core.AuthenticationException;

;

public class ErrorValidationTokenException extends AuthenticationException {
  public ErrorValidationTokenException(String message) {
    super(message);
  }
}
