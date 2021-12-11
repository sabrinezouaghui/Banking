package com.kata.Bank.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Exception to indicate that the given data is invalid
 *
 * @author Sabrine.zouaghi
 */
public class InvalidDataException extends KataBankRuntimeException {
  /**
   * generated serial ID
   */
  private static final long serialVersionUID = 1L;

  /**
   * Exception status
   */
  private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

  /**
   * Exception code
   */
  private static final String CODE = "INVALID_DATA_EXCEPTION";

  /**
   * Exception message
   */
  private static final String MESSAGE = "INVALID DATA";

  /**
   * Default constructor
   */
  public InvalidDataException() {
    this(CODE, MESSAGE);
  }

  /**
   * Customised constructor
   *
   * @param message : Customized exception message
   */
  public InvalidDataException(String message) {
    this(CODE, message);
  }

  /**
   * Customised constructor
   *
   * @param code    : Customized exception code
   * @param message : Customized exception message
   */
  public InvalidDataException(String code, String message) {
    super(STATUS, code, message);
  }

}