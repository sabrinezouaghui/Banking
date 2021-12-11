package com.kata.Bank.exceptions.filter;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Exception details
 *
 * @author Sabrine.zouaghi
 */
@Getter
@Setter
public class DataExceptionDto {

  /**
   * Exception date
   */
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  private ZonedDateTime timestamp;

  /**
   * Exception code
   */
  private int code;

  /**
   * Exception status
   */
  private String status;

  /**
   * Exception message
   */
  private String message;

  public DataExceptionDto() {
    timestamp = ZonedDateTime.now(ZoneId.of("UTC"));
  }

  public DataExceptionDto(HttpStatus httpStatus, String message) {
    this();
    this.code = httpStatus.value();
    this.status = httpStatus.name();
    this.message = message;
  }
}
