package com.kata.Bank.rest.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.ZonedDateTime;

/**
 * Request of retrieve Account Statement
 *
 * @author Sabrine.zouaghi
 */

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RetrieveAccountStatementRequest {

  /**
   * accountId
   */
  private String accountId;

  /**
   * endDate
   */
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private ZonedDateTime endDate;

  /**
   * startDate
   */
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private ZonedDateTime startDate;

  /**
   * page
   */
  private int page;

  /**
   * size
   */
  private int size;
}
