package com.smartcut.app.Error;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestControllerAdvice
public class ExceptionManager {


  public ResponseEntity<ErrorResponse> generalExceptionHandler(String exceptionMassage, HttpStatus httpStatus) {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    ErrorResponse error = new ErrorResponse(
        LocalDateTime.now().format(formatter),
        httpStatus.value(),
        httpStatus.getReasonPhrase(),
        exceptionMassage
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
  public ResponseEntity<List<ErrorArgumentResponse>> handlerArgumentInvalid(MethodArgumentNotValidException exception) {

    var errors = exception.getFieldErrors()
                          .stream()
                          .map(ErrorArgumentResponse::new)
                          .toList();

    return ResponseEntity.badRequest().body(errors);
  }

  /**
   * Este método permite manejar la excepción que se lanzan al momento de verificar si el variable pasada por parametro es valida.
   *
   * @param exception
   * @return Retorna un ErrorArgumentResponse el cual contiene el mensaje de error
   */
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorArgumentResponse> handlerCommandInvalid(ConstraintViolationException exception) {

    var errors = exception.getConstraintViolations()
                          .stream()
                          .map(ErrorArgumentResponse::new)
                          .findFirst()
                          .get();

    return ResponseEntity.badRequest().body(errors);
  }

  /**
   * Este método permite manejar la excepción que se lanza en el momento en que las credenciales para autenticarse son incorrectas
   * @return un ErrorResponse que contiene información de la excepción lanzada a partir de la logica de negocio
   */
  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ErrorResponse> handlerBadCredential() {
    return this.generalExceptionHandler("Credenciales Incorrectas", HttpStatus.UNAUTHORIZED);
  }

  /**
   * Este método permite manejar la excepción que se lanza en el momento en que un material buscado no se encuentra en la BD
   * @param exception
   * @return un ErrorResponse que contiene información de la excepción lanzada a partir de la logica de negocio
   */
  @ExceptionHandler(MaterialNotFoundException.class)
  public ResponseEntity<ErrorResponse> handlerMaterialNotFound(MaterialNotFoundException exception) {
    return this.generalExceptionHandler(exception.getMessage(), HttpStatus.NOT_FOUND);
  }

  /**
   * Este método permite manejar la excepción que se lanza en el momento en que un proveedor buscado no se encuentra en la BD
   * @param exception
   * @return un ErrorResponse que contiene información de la excepción lanzada a partir de la logica de negocio
   */
  @ExceptionHandler(SupplierNotFoundException.class)
  public ResponseEntity<ErrorResponse> handlerNotFound(SupplierNotFoundException exception) {
    return this.generalExceptionHandler(exception.getMessage(), HttpStatus.NOT_FOUND);
  }

  /**
   * Este método permite manejar la excepción que se lanza en el momento en que un proveedor ya se encuentra registrado en la BD
   * @param exception
   * @return ErrorResponse que contiene información de la excepción lanzada a partir de la logica de negocio
   */
  @ExceptionHandler(SupplierAlreadyExitsException.class)
  public ResponseEntity<ErrorResponse> handlerAlreadyExits(SupplierAlreadyExitsException exception) {
    return this.generalExceptionHandler(exception.getMessage(), HttpStatus.CONFLICT);
  }

  /**
   * Este método permite manejar la excepción que se lanza en el momento en que un usuaro no se encontrado en la BD
   * @param exception
   * @return ErrorResponse que contiene información de la excepción lanzada a partir de la logica de negocio
   */
  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ErrorResponse> handlerUserNotFound(UserNotFoundException exception) {
    return this.generalExceptionHandler(exception.getMessage(), HttpStatus.NOT_FOUND);
  }

  /**
   * Este método permite manejar la excepción que se lanza en el momento en que un usuario no tiene los permisos
   * para consultar datos de otro usuario o modificar la información.
   * @param exception
   * @return ErrorResponse que contiene información de la excepción lanzada a partir de la logica de negocio
   */

  @ExceptionHandler(WithoutPermitsException.class)
  public ResponseEntity<ErrorResponse> handlerWithoutPermitsException(WithoutPermitsException exception) {
    return this.generalExceptionHandler(exception.getMessage(), HttpStatus.FORBIDDEN);
  }

  /**
   * Este método permite manejar la excepción que se lanza en el momento en que se quiere registrar un usuario con un Username en uso.
   * @param exception
   * @return ErrorResponse que contiene información de la excepción lanzada a partir de la logica de negocio
   */

  @ExceptionHandler(UsernameAlreadyExistException.class)
  public ResponseEntity<ErrorResponse> handlerUsernameAlreadyExist(UsernameAlreadyExistException exception) {
    return this.generalExceptionHandler(exception.getMessage(), HttpStatus.CONFLICT);
  }


}
