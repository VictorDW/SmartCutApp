package com.smartcut.app.error;

import com.smartcut.app.util.DateUtils;
import com.smartcut.app.util.MessageUtil;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.List;
@AllArgsConstructor
@ControllerAdvice
public class ExceptionManager {

  private final MessageUtil messageComponent;


  public static ResponseEntity<ErrorResponse> generalExceptionHandler(String exceptionMassage, HttpStatus httpStatus) {

    ErrorResponse error = new ErrorResponse(
        DateUtils.dateFormat(LocalDateTime.now()),
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
   * Este método permite manejar la excepción que se lanzan al momento de verificar si la variable pasada por parametro es válida.
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
                          .orElseThrow();

    return ResponseEntity.badRequest().body(errors);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ErrorArgumentResponse> handlerTypeInvalid(MethodArgumentTypeMismatchException exception) {
    return ResponseEntity.badRequest().body(
            new ErrorArgumentResponse(exception.getName(), messageComponent.getMessage("message.error.type"))
    );
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorResponse> handlerHttpNotReadable(HttpMessageNotReadableException exception) {
    return generalExceptionHandler(exception.getMessage(), HttpStatus.BAD_REQUEST);
  }
  /**
   * Este método permite manejar la excepción que se lanza en el momento en que las credenciales para autenticarse son incorrectas
   * @return un ErrorResponse que contiene información de la excepción lanzada a partir de la logica de negocio
   */
  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ErrorResponse> handlerBadCredential() {
    return generalExceptionHandler(messageComponent.getMessage("message.error.bad.credential"), HttpStatus.UNAUTHORIZED);
  }

  /**
   * Este método permite manejar la excepción que se lanza en el momento en que un usuario deshabilitado intenta autenticarse
   * @return un ErrorResponse que contiene información de la excepción lanzada a partir de la logica de negocio
   */
  @ExceptionHandler(DisabledException.class)
  public ResponseEntity<ErrorResponse> handlerDisableException() {
    return ExceptionManager.generalExceptionHandler(messageComponent.getMessage("message.error.disable.user"), HttpStatus.UNAUTHORIZED);
  }

  /**
   * Este método permite manejar la excepción que se lanza en el momento en que un material buscado no se encuentra en la BD
   * @param exception
   * @return un ErrorResponse que contiene información de la excepción lanzada a partir de la logica de negocio
   */
  @ExceptionHandler(MaterialNotFoundException.class)
  public ResponseEntity<ErrorResponse> handlerMaterialNotFound(MaterialNotFoundException exception) {
    return generalExceptionHandler(exception.getMessage(), HttpStatus.NOT_FOUND);
  }

  /**
   * Este método permite manejar la excepción que se lanza en el momento en que un proveedor buscado no se encuentra en la BD
   * @param exception
   * @return un ErrorResponse que contiene información de la excepción lanzada a partir de la logica de negocio
   */
  @ExceptionHandler(SupplierNotFoundException.class)
  public ResponseEntity<ErrorResponse> handlerSupplierNotFound(SupplierNotFoundException exception) {
    return generalExceptionHandler(exception.getMessage(), HttpStatus.NOT_FOUND);
  }

  /**
   * Este método permite manejar la excepción que se lanza en el momento en que un proveedor ya se encuentra registrado en la BD
   * @param exception
   * @return ErrorResponse que contiene información de la excepción lanzada a partir de la logica de negocio
   */
  @ExceptionHandler(SupplierAlreadyExistException.class)
  public ResponseEntity<ErrorResponse> handlerSupplierAlreadyExits(SupplierAlreadyExistException exception) {
    return generalExceptionHandler(exception.getMessage(), HttpStatus.CONFLICT);
  }

  /**
   * Este método permite manejar la excepción que se lanza en el momento en que un usuaro no se encontrado en la BD
   * @param exception
   * @return ErrorResponse que contiene información de la excepción lanzada a partir de la logica de negocio
   */
  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ErrorResponse> handlerUserNotFound(UserNotFoundException exception) {
    return generalExceptionHandler(exception.getMessage(), HttpStatus.NOT_FOUND);
  }

  /**
   * Este método permite manejar la excepción que se lanza en el momento en que un usuario no tiene los permisos
   * para consultar datos de otro usuario o modificar la información.
   * @param exception
   * @return ErrorResponse que contiene información de la excepción lanzada a partir de la logica de negocio
   */

  @ExceptionHandler(WithoutPermitsException.class)
  public ResponseEntity<ErrorResponse> handlerWithoutPermitsException(WithoutPermitsException exception) {
    return generalExceptionHandler(exception.getMessage(), HttpStatus.FORBIDDEN);
  }

  /**
   * Este método permite manejar la excepción que se lanza en el momento en que se quiere registrar un usuario con un Username en uso.
   * @param exception
   * @return ErrorResponse que contiene información de la excepción lanzada a partir de la logica de negocio
   */

  @ExceptionHandler(UsernameAlreadyExistException.class)
  public ResponseEntity<ErrorResponse> handlerUsernameAlreadyExist(UsernameAlreadyExistException exception) {
    return generalExceptionHandler(exception.getMessage(), HttpStatus.CONFLICT);
  }

  @ExceptionHandler(UserAlreadyExistException.class)
  public ResponseEntity<ErrorResponse> handlerUserAlreadyExits(UserAlreadyExistException exception) {
    return generalExceptionHandler(exception.getMessage(), HttpStatus.CONFLICT);
  }

}
