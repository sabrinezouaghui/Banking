package com.kata.Bank.exceptions.filter;

import com.kata.Bank.exceptions.InsufficientBalanceException;
import com.kata.Bank.exceptions.InvalidDataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Response filter
 *
 * @author Sabrine.zouaghi
 */
@ControllerAdvice
public class ResponseExceptionFilter {

  /**
   * Handle invalid data exception
   *
   * @param ex : Exception
   * @return {@link ResponseEntity} of {@link DataExceptionDto}
   */
  @ExceptionHandler(InvalidDataException.class)
  public ResponseEntity<DataExceptionDto> mapInvalidInputException(InvalidDataException ex) {
    DataExceptionDto error = new DataExceptionDto(HttpStatus.BAD_REQUEST, ex.getMessage());
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  /**
   * Handle insufficient balance exception
   *
   * @param ex : Exception
   * @return {@link ResponseEntity} of {@link DataExceptionDto}
   */
  @ExceptionHandler(InsufficientBalanceException.class)
  public ResponseEntity<DataExceptionDto> mapInsufficientBalanceException(InsufficientBalanceException ex) {
    DataExceptionDto error = new DataExceptionDto(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * Handle invalid arguments exception
   *
   * @param ex : Exception
   * @return {@link ResponseEntity} of {@link DataExceptionDto}
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<DataExceptionDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    List<String> errors = new ArrayList<>();
    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
      errors.add(String.join("", Arrays.asList(error.getField(), ": ", error.getDefaultMessage())));
    }
    for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
      errors.add(String.join("", Arrays.asList(error.getObjectName(), ": ", error.getDefaultMessage())));
    }
    DataExceptionDto error = new DataExceptionDto(HttpStatus.BAD_REQUEST, String.join("", errors));
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }
}