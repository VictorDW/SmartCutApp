package com.smartcut.app.Error;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class HandlerException {


  public ResponseEntity<ErrorResponse> exceptionHandler(RuntimeException exception, WebRequest request, HttpStatus httpStatus) {

    ErrorResponse error = new ErrorResponse(
        LocalDateTime.now(),
        httpStatus.value(),
        httpStatus.getReasonPhrase(),
        exception.getMessage(),
        request.getDescription(false)
    );
    return new ResponseEntity<>(error, httpStatus);
  }

  /**
   * Este manejador de excepcion permite responder al cliente, con los errores en las validaciones de la request
   *
   * @param exception
   * @return Retorna una lista de ErrorArgumentResponse los cuales contiene el atributo y el mensaje de error
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<List<ErrorArgumentResponse>> handlerArgumentInvalidException(MethodArgumentNotValidException exception) {

    var errors = exception.getFieldErrors()
                          .stream()
                          .map(ErrorArgumentResponse::new)
                          .toList();

    return ResponseEntity.badRequest().body(errors);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<List<ErrorArgumentResponse>> handlerCommandInvalidException(ConstraintViolationException exception, WebRequest request) {
    var errors = exception.getConstraintViolations().stream().map(ErrorArgumentResponse::new).toList();
    return ResponseEntity.badRequest().body(errors);
    //return this.exceptionHandler(exception, request, HttpStatus.BAD_REQUEST);
  }

}
