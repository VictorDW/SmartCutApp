package com.smartcut.app.Error;

import jakarta.validation.ConstraintViolation;
import org.springframework.validation.FieldError;

public record ErrorArgumentResponse(
    String fieldName,
    String message
) {

  public ErrorArgumentResponse(FieldError error) {
    this(error.getField(), error.getDefaultMessage());
  }

  public ErrorArgumentResponse(ConstraintViolation error) {
    this("Parámetro de solicitud", error.getMessage());
  }
}
