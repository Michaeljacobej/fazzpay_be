package io.github.fazzpay.fazzpay.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import io.github.fazzpay.fazzpay.exceptions.custom.ResourceNotEnoughException;
import io.github.fazzpay.fazzpay.exceptions.custom.IncorectPinException;
import io.github.fazzpay.fazzpay.exceptions.custom.ResourceAlreadyExistException;
import io.github.fazzpay.fazzpay.exceptions.custom.ResourceNotFoundException;
import io.github.fazzpay.fazzpay.exceptions.custom.TransferSelfException;
import io.github.fazzpay.fazzpay.payloads.response.ResponseError;

@ControllerAdvice
public class ResponseExceptionHandler {

  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseEntity<Object> handleNoHandlerException(NoHandlerFoundException e) {
    ResponseError responseError = new ResponseError(HttpStatus.NOT_FOUND, e.getMessage(), null);
    return ResponseEntity.status(responseError.getStatus()).body(responseError);
  }

  @ExceptionHandler(value = AuthenticationException.class)
  public ResponseEntity<?> handleAuthenticationException(AuthenticationException e) {
    ResponseError responseError = new ResponseError(HttpStatus.UNAUTHORIZED, e.getMessage(), null);
    return ResponseEntity.status(responseError.getStatus()).body(responseError);
  }

  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  public ResponseEntity<?> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
    Map<String, String> errors = new HashMap<>();

    e.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

    ResponseError responseError = new ResponseError(e.getStatusCode(),
        "Error Request", errors);

    return ResponseEntity.status(responseError.getStatus()).body(responseError);
  }

  @ExceptionHandler(value = ResourceNotFoundException.class)
  public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException e) {
    ResponseError responseError = new ResponseError(HttpStatus.NOT_FOUND, e.getMessage(), null);
    return ResponseEntity.status(responseError.getStatus()).body(responseError);
  }

  @ExceptionHandler(value = NullPointerException.class)
  public ResponseEntity<?> handleNullPointerException(NullPointerException e) {
    ResponseError responseError = new ResponseError(HttpStatus.NOT_ACCEPTABLE, e.getMessage(), null);
    return ResponseEntity.status(responseError.getStatus()).body(responseError);
  }

  @ExceptionHandler(value = IncorectPinException.class)
  public ResponseEntity<?> handleIncorectPinException(IncorectPinException e) {
    ResponseError responseError = new ResponseError(HttpStatus.NOT_ACCEPTABLE, e.getMessage(), null);
    return ResponseEntity.status(responseError.getStatus()).body(responseError);
  }

  @ExceptionHandler(value = ResourceAlreadyExistException.class)
  public ResponseEntity<?> handleResourceAlreadyExistException(ResourceAlreadyExistException e) {
    ResponseError responseError = new ResponseError(HttpStatus.BAD_REQUEST, e.getMessage(), null);
    return ResponseEntity.status(responseError.getStatus()).body(responseError);
  }

  @ExceptionHandler(value = TransferSelfException.class)
  public ResponseEntity<?> handleTransferSelfException(TransferSelfException e) {
    ResponseError responseError = new ResponseError(HttpStatus.BAD_REQUEST, e.getMessage(), null);
    return ResponseEntity.status(responseError.getStatus()).body(responseError);
  }

  @ExceptionHandler(value = BadCredentialsException.class)
  public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException e) {
    ResponseError responseError = new ResponseError(HttpStatus.UNAUTHORIZED, e.getMessage(), null);
    return ResponseEntity.status(responseError.getStatus()).body(responseError);
  }

  @ExceptionHandler(value = DataIntegrityViolationException.class)
  public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
    ResponseError responseError = new ResponseError(HttpStatus.BAD_REQUEST, e.getMessage(), null);
    return ResponseEntity.status(responseError.getStatus()).body(responseError);
  }

  @ExceptionHandler(value = HttpMessageNotReadableException.class)
  public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
    ResponseError responseError = new ResponseError(HttpStatus.BAD_REQUEST, e.getMessage(), null);
    return ResponseEntity.status(responseError.getStatus()).body(responseError);
  }

  @ExceptionHandler(value = ResourceNotEnoughException.class)
  public ResponseEntity<?> handleNotEnoughStockException(ResourceNotEnoughException e) {
    ResponseError responseError = new ResponseError(HttpStatus.NOT_ACCEPTABLE, e.getMessage(), null);
    return ResponseEntity.status(responseError.getStatus()).body(responseError);
  }

  @ExceptionHandler(value = Exception.class)
  public ResponseEntity<?> handleExceptionGlobal(Exception e) {
    ResponseError responseError = new ResponseError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null);
    return ResponseEntity.status(responseError.getStatus()).body(responseError);
  }

}