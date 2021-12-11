package com.kata.Bank.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Exception to indicate Kata bank generic unexpected behaviours
 *
 * @author Sabrine.zouaghi
 */
public class KataBankRuntimeException extends RuntimeException {

	/**
	 * generated serial ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Exception status
	 */
	protected HttpStatus httpStatus;

	/**
	 * Exception code
	 */
	protected String code;

	/**
	 * Exception data
	 */
	protected Object data;

	/**
	 * Customised constructor
	 *
	 * @param httpStatus : Customized exception status
	 * @param code       : Customized exception code
	 * @param message    : Customized exception message
	 */
	public KataBankRuntimeException(HttpStatus httpStatus, String code, String message) {
		super(message);
		this.httpStatus = httpStatus;
		this.code = code;
	}

	/**
	 * Customised constructor
	 *
	 * @param httpStatus : Customized exception status
	 * @param code       : Customized exception code
	 * @param message    : Customized exception message
	 * @param data       : Customized exception data
	 */
	public KataBankRuntimeException(HttpStatus httpStatus, String code, String message, Object data) {
		super(message);
		this.httpStatus = httpStatus;
		this.code = code;
		this.data = data;
	}
}