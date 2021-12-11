package com.kata.Bank.service.impl;

import com.kata.Bank.exceptions.InsufficientBalanceException;
import com.kata.Bank.exceptions.InvalidDataException;
import com.kata.Bank.models.Account;
import com.kata.Bank.models.AccountStatement;
import com.kata.Bank.models.enumeration.OperationAccount;
import com.kata.Bank.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Account Service class
 *
 * @author Sabrine.zouaghi
 */
@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

  private static final String NEGATIVE_AMOUNT = "Amount to deposit must not be negative";
  private static final String ACCOUNT_ID_PROVIDED = "Account Id must be provided";
  private static final String ACCOUNT_NOT_EXIST = "Account with id %s not exist";
  private static final String INSUFFICIENT_BALANCE = "You don't have insufficient balance in your Account";
  private static final String START_END_DATE_INVALID = "The start date must be before the end date";
  private static final String NOT_VALID_PAGE = "Not Valid Page";
  private static final String NOT_VALID_ELEMENT = "Not Valid nbElements";

  // I used accounts and accountStatements to replace persistence in Data Base
  private Set<Account> accounts = new HashSet<>();
  private Set<AccountStatement> accountStatements = new HashSet<>();

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<Account> retrieveAccountById(String accountId) {

    if (accountId == null)
      throw new InvalidDataException(ACCOUNT_ID_PROVIDED);

    // TODO implement persistence part to retrieve the correct details

    // TODO In this method I mocked the response to cover all the test cases so when accountId does not exist in accounts the result will be empty otherwise return account details

    return accounts.stream()
                   .filter(account -> account.getId()
                                             .equals(accountId))
                   .findFirst();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<AccountStatement> retrieveAccountStatementByAccountId(String accountId) {
    if (accountId == null)
      throw new InvalidDataException(ACCOUNT_ID_PROVIDED);
    // TODO implement persistence part to retrieve the correct details

    // TODO In this method I mocked the response to cover all the test cases so when accountId does not exist in accountStatements the result will be empty otherwise return accountStatements details
    return accountStatements.stream()
                            .filter(accountStatement -> accountStatement.getAccountId()
                                                                        .equals(accountId))
                            .collect(Collectors.toList());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Account createAccount(String userId, int amount, String accountId) {
    Account newAccount = Account.builder()
                                .idUser(userId)
                                .id(accountId)
                                .currentBalance(amount)
                                .build();
    // TODO save Account in Data Base
    AccountStatement newStatement = AccountStatement.builder()
                                                    .accountId(newAccount.getId())
                                                    .id(UUID.randomUUID()
                                                            .toString())
                                                    .amount(amount)
                                                    .balanceAccount(amount)
                                                    .operation(OperationAccount.DEPOSIT_MONEY)
                                                    .dateOfOperation(ZonedDateTime.now(ZoneId.of("UTC")))
                                                    .build();
    // TODO save Account Statement in Data Base
    accounts.add(newAccount); // I use this list to mocked Data Base response
    accountStatements.add(newStatement); // I use this list to mocked Data Base response
    return newAccount;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void depositMoney(int amount, String accountId) {
    if (amount <= 0) {
      log.error(NEGATIVE_AMOUNT);
      throw new InvalidDataException(NEGATIVE_AMOUNT);
    }
    if (ObjectUtils.isEmpty(accountId)) {
      log.error(ACCOUNT_ID_PROVIDED);
      throw new InvalidDataException(ACCOUNT_ID_PROVIDED);
    }
    Optional<Account> accountResult = retrieveAccountById(accountId);
    if (accountResult.isPresent()) {
      Account account = accountResult.get();
      int currentBalance = account.getCurrentBalance() + amount;
      account.setCurrentBalance(currentBalance);
      // TODO save new amount in Data Base
      AccountStatement newStatement = AccountStatement.builder()
                                                      .accountId(accountId)
                                                      .id(UUID.randomUUID()
                                                              .toString())
                                                      .amount(amount)
                                                      .balanceAccount(currentBalance)
                                                      .operation(OperationAccount.DEPOSIT_MONEY)
                                                      .dateOfOperation(ZonedDateTime.now(ZoneId.of("UTC")))
                                                      .build();
      // TODO save new statement in Data Base
      accountStatements.add(newStatement); // I use this list to mocked Data Base response
    } else {
      log.error(String.format(ACCOUNT_NOT_EXIST, accountId));
      throw new InvalidDataException(String.format(ACCOUNT_NOT_EXIST, accountId));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void withdrawMoney(int amount, String accountId) {

    if (amount <= 0){
      log.error(NEGATIVE_AMOUNT);
      throw new InvalidDataException(NEGATIVE_AMOUNT);
    }

    if (ObjectUtils.isEmpty(accountId)){
      log.error(ACCOUNT_ID_PROVIDED);
      throw new InvalidDataException(ACCOUNT_ID_PROVIDED);
    }

    Optional<Account> accountResult = retrieveAccountById(accountId);
    if (accountResult.isPresent()) {
      Account account = accountResult.get();
      if (account.getCurrentBalance() < amount) {
        log.error(INSUFFICIENT_BALANCE);
        throw new InsufficientBalanceException(INSUFFICIENT_BALANCE);
      }
      int currentBalance = account.getCurrentBalance() - amount;
      account.setCurrentBalance(currentBalance);
      // TODO save new amount in Data Base
      AccountStatement newStatement = AccountStatement.builder()
                                                      .accountId(accountId)
                                                      .id(UUID.randomUUID()
                                                              .toString())
                                                      .amount(amount)
                                                      .balanceAccount(currentBalance)
                                                      .operation(OperationAccount.WITHDRAW_MONEY)
                                                      .dateOfOperation(ZonedDateTime.now(ZoneId.of("UTC")))
                                                      .build();
      // TODO save new statement in Data Base
      accountStatements.add(newStatement);// I use this list to mocked Data Base response
    } else {
      log.error(String.format(ACCOUNT_NOT_EXIST, accountId));
      throw new InvalidDataException(String.format(ACCOUNT_NOT_EXIST, accountId));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Page<AccountStatement> retrieveAccountStatement(String accountId, ZonedDateTime startDate, ZonedDateTime endDate, int page,
      int nbElement) {

    if (ObjectUtils.isEmpty(accountId)){
      log.error(ACCOUNT_ID_PROVIDED);
      throw new InvalidDataException(ACCOUNT_ID_PROVIDED);
    }

    if (startDate != null && endDate != null && endDate.compareTo(startDate) < 0) {
      log.error(START_END_DATE_INVALID);
      throw new InvalidDataException(START_END_DATE_INVALID);
    }
    validatePage(page, nbElement);
    Pageable pageable = PageRequest.of(page, nbElement);
    Optional<Account> accountResult = retrieveAccountById(accountId);
    if (accountResult.isPresent()) {
      List<AccountStatement> accountStatements = retrieveAccountStatementByAccountId(accountId);

      if (!CollectionUtils.isEmpty(accountStatements)) {

        if (startDate != null && endDate != null) {
          accountStatements = accountStatements.stream()
                                               .filter(accountStatement -> (accountStatement.getDateOfOperation()
                                                                                            .compareTo(startDate) >= 0) && (
                                                   accountStatement.getDateOfOperation()
                                                                   .compareTo(endDate) <= 0))
                                               .collect(Collectors.toList());

        }
        if (startDate != null && endDate == null) {
          accountStatements = accountStatements.stream()
                                               .filter(accountStatement -> (accountStatement.getDateOfOperation()
                                                                                            .compareTo(startDate) >= 0))
                                               .collect(Collectors.toList());

        }

        if (startDate == null && endDate != null) {
          accountStatements = accountStatements.stream()
                                               .filter(accountStatement -> (accountStatement.getDateOfOperation()
                                                                                            .compareTo(endDate) <= 0))
                                               .collect(Collectors.toList());

        }

      }

      return convertListToPage(accountStatements, pageable);
    } else {
      log.error(String.format(ACCOUNT_NOT_EXIST, accountId));
      throw new InvalidDataException(String.format(ACCOUNT_NOT_EXIST, accountId));
    }
  }

  /**
   * Convert {@link Set} to {@link Page}
   *
   * @param accountStatements Account Statement List
   * @param pageable          Page Details
   * @return {@link Page} of {@link AccountStatement}
   */
  private Page<AccountStatement> convertListToPage(List<AccountStatement> accountStatements, Pageable pageable) {
    int start = (int) pageable.getOffset();
    int end = Math.min((start + pageable.getPageSize()), accountStatements.size());
    try {
      return new PageImpl<>(accountStatements.subList(start, end), pageable, accountStatements.size());
    } catch (Exception e) {
      return new PageImpl<>(new ArrayList<>(), pageable, accountStatements.size());
    }

  }

  /**
   * validate Page details
   *
   * @param page       page
   * @param nbElements number element in page
   * @throws InvalidDataException
   */
  private void validatePage(int page, int nbElements) {
    if (page < 0) {
      log.error(NOT_VALID_PAGE);
      throw new InvalidDataException(NOT_VALID_PAGE);
    }
    if (nbElements < 1) {
      log.error(NOT_VALID_PAGE);
      throw new InvalidDataException(NOT_VALID_ELEMENT);
    }
  }
}