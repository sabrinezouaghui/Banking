package com.kata.Bank.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Exception to indicate that the given balance is insufficient
 *
 * @author Sabrine.zouaghi
 */
public class InsufficientBalanceException extends KataBankRuntimeException {
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
  private static final String CODE = "INSUFFICIENT_BALANCE_EXCEPTION";

  /**
   * Exception message
   */
  private static final String MESSAGE = "INSUFFICIENT BALANCE";

  /**
   * Default constructor
   */
  public InsufficientBalanceException() {
    this(CODE, MESSAGE);
  }

  /**
   * Customised constructor
   *
   * @param message : Customized exception message
   */
  public InsufficientBalanceException(String message) {
    this(CODE, message);
  }

  /**
   * Customised constructor
   *
   * @param code    : : Customized exception code
   * @param message : Customized exception message
   */
  public InsufficientBalanceException(String code, String message) {
    super(STATUS, code, message);
  }

}
