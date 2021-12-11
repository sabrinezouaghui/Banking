package com.kata.Bank.models;

import com.kata.Bank.models.enumeration.OperationAccount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

/**
 * The Account Statement class, save all operations made in each account
 *
 * @author Sabrine.zouaghi
 */

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountStatement {

  /**
   * Account Statement Identifier
   */
  private String id;

  /**
   * Account Identifier
   */
  private String accountId;

  /**
   * date of operation
   */
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

  /**
   * User Identifier
   */
  private String idUser;

}
