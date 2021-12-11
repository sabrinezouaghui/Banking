package com.kata.Bank.service;

import com.kata.Bank.models.Account;
import com.kata.Bank.models.AccountStatement;
import org.springframework.data.domain.Page;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Account Service interface
 *
 * @author Sabrine.zouaghi
 */
public interface AccountService {

  /**
   * Retrieve Account Details by Id
   *
   * @param accountId : Account Id
   * @return {@link Optional} of {@link Account}
   */
  Optional<Account> retrieveAccountById(String accountId);

  /**
   * Retrieve Account Statement by Account Id
   *
   * @param accountId : Account Id
   * @return {@link List} of {@link AccountStatement}
   */
  List<AccountStatement> retrieveAccountStatementByAccountId(String accountId);

  /**
   * Create Account for User
   *
   * @param userId : User id
   * @param amount : Amount to deposit
   * @return {@link Account}
   */
  Account createAccount(String userId, int amount, String accountId);

  /**
   * Deposit Money in each Account
   *
   * @param amount :Amount to deposit
   * @param accountId : Account Identifier
   */
  void depositMoney(int amount, String accountId);

  /***
   * Withdrawal Money in each Account
   *
   * @param amount : Amount to withdrawal
   * @param accountId : Account Identifier
   */
  void withdrawMoney(int amount, String accountId);

  /**
   * Retrieve Page of Account Statement By Account Id in a specific period
   *
   * @param accountId : Account Identifier
   * @param startDate : Start Date of Debut Statement
   * @param endDate : End Date of final Statement
   * @param page : Page number
   * @param nbElement : Elements number
   * @return {@link Page} of {@link AccountStatement}
   */
  Page<AccountStatement> retrieveAccountStatement(String accountId, ZonedDateTime startDate, ZonedDateTime endDate, int page,
      int nbElement);

}