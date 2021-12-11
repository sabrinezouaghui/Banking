package com.kata.Bank.rest.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Request of deposit Amount in Account
 *
 * @author Sabrine.zouaghi
 */

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepositAmountRequest {

  /**
   * Account Id
   */
  private String accountId;

  /**
   * Amount
   */
  private int amount;
}