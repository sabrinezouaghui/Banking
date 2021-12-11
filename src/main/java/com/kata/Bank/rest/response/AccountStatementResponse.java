package com.kata.Bank.rest.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kata.Bank.models.enumeration.OperationAccount;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.ZonedDateTime;

/**
 * Account Statement Dto
 *
 * @author Sabrine.zouaghi
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountStatementResponse {

  /**
   * Date of operation
   */
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private ZonedDateTime dateOfOperation;

  /**
   * Deposit or Withdraw money
   */
  private int amount;

  /**
   * Balance Account
   */
  private int balanceAccount;

  /**
   * operation type
   */
  private OperationAccount operation;
}
