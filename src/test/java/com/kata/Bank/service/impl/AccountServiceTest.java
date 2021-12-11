package com.kata.Bank.service.impl;

import com.kata.Bank.exceptions.InsufficientBalanceException;
import com.kata.Bank.exceptions.InvalidDataException;
import com.kata.Bank.models.Account;
import com.kata.Bank.models.AccountStatement;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Sabrine.zouaghi
 */
@ExtendWith(SpringExtension.class)
public class AccountServiceTest {

  private static final String ACCOUNT_ID = "account_id";

  @InjectMocks
  private AccountServiceImpl accountService;

  @Test
  public void depositMoney_ThrowsException_ifAmountNegative() {
    Exception exception = assertThrows(InvalidDataException.class, () -> {
      accountService.depositMoney(-1000, ACCOUNT_ID);
    });
    Exception exception_1 = assertThrows(InvalidDataException.class, () -> {
      accountService.depositMoney(0, ACCOUNT_ID);
    });
    String expectedMessage = "Amount to deposit must not be negative";
    String actualMessage = exception.getMessage();
    String actualMessage_1 = exception_1.getMessage();
    assertTrue(actualMessage.contains(expectedMessage));
    assertTrue(actualMessage_1.contains(expectedMessage));
  }

  @Test
  public void depositMoney_ThrowsException_ifAccountIdNull() {
    Exception exception = assertThrows(InvalidDataException.class, () -> {
      accountService.depositMoney(1000, null);
    });
    Exception exception_1 = assertThrows(InvalidDataException.class, () -> {
      accountService.depositMoney(1000, "");
    });
    String expectedMessage = "Account Id must be provided";
    String actualMessage = exception.getMessage();
    String actualMessage_1 = exception_1.getMessage();
    assertTrue(actualMessage.contains(expectedMessage));
    assertTrue(actualMessage_1.contains(expectedMessage));
  }

  @Test
  public void depositMoney_ThrowsException_ifAccountIdNotExist() {
    Exception exception = assertThrows(InvalidDataException.class, () -> {
      accountService.depositMoney(1000, "123456789");
    });
    String expectedMessage = "Account with id 123456789 not exist";
    String actualMessage = exception.getMessage();
    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  public void depositMoney() {
    String accountId = UUID.randomUUID()
                           .toString();
    accountService.createAccount("147852", 200, accountId);
    int dataBaseSize = accountService.retrieveAccountStatementByAccountId(accountId)
                                     .size();
    accountService.depositMoney(100, accountId);
    Optional<Account> accountDetails = accountService.retrieveAccountById(accountId);
    List<AccountStatement> accountStatementDetails = accountService.retrieveAccountStatementByAccountId(accountId);
    Assertions.assertThat(accountDetails.isPresent());
    Assertions.assertThat(accountDetails.get()
                                        .getId())
              .isEqualTo(accountId);
    Assertions.assertThat(accountDetails.get()
                                        .getCurrentBalance())
              .isEqualTo(300);

    Assertions.assertThat(accountStatementDetails.size())
              .isEqualTo(dataBaseSize + 1);
  }

  @Test
  public void withdrawMoney_ThrowsException_ifAmountNegative() {
    Exception exception = assertThrows(InvalidDataException.class, () -> {
      accountService.depositMoney(-1000, ACCOUNT_ID);
    });
    Exception exception_1 = assertThrows(InvalidDataException.class, () -> {
      accountService.depositMoney(0, ACCOUNT_ID);
    });
    String expectedMessage = "Amount to deposit must not be negative";
    String actualMessage = exception.getMessage();
    String actualMessage_1 = exception_1.getMessage();
    assertTrue(actualMessage.contains(expectedMessage));
    assertTrue(actualMessage_1.contains(expectedMessage));
  }

  @Test
  public void withdrawMoney_ThrowsException_ifAccountIdNull() {
    Exception exception = assertThrows(InvalidDataException.class, () -> {
      accountService.depositMoney(1000, null);
    });
    Exception exception_1 = assertThrows(InvalidDataException.class, () -> {
      accountService.depositMoney(1000, "");
    });
    String expectedMessage = "Account Id must be provided";
    String actualMessage = exception.getMessage();
    String actualMessage_1 = exception_1.getMessage();
    assertTrue(actualMessage.contains(expectedMessage));
    assertTrue(actualMessage_1.contains(expectedMessage));
  }

  @Test
  public void withdrawMoney_ThrowsException_ifAccountIdNotExist() {
    Exception exception = assertThrows(InvalidDataException.class, () -> {
      accountService.depositMoney(1000, "123456789");
    });
    String expectedMessage = "Account with id 123456789 not exist";
    String actualMessage = exception.getMessage();
    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  public void withdrawMoney_ThrowsException_ifAmountNotValid() {
    String accountId = UUID.randomUUID()
                           .toString();
    accountService.createAccount("147852", 200, accountId);
    int dataBaseSize = accountService.retrieveAccountStatementByAccountId(accountId)
                                     .size();
    Exception exception = assertThrows(InsufficientBalanceException.class, () -> {
    accountService.withdrawMoney(300, accountId);
    });
    int dataBaseSize_afterAction = accountService.retrieveAccountStatementByAccountId(accountId)
                                     .size();
    String expectedMessage = "You don't have insufficient balance in your Account";
    String actualMessage = exception.getMessage();
    assertTrue(actualMessage.contains(expectedMessage));
    assertEquals(dataBaseSize,dataBaseSize_afterAction);
  }

  @Test
  public void withdrawMoney() {
    String accountId = UUID.randomUUID()
                           .toString();
    accountService.createAccount("147852", 200, accountId);
    int dataBaseSize = accountService.retrieveAccountStatementByAccountId(accountId)
                                     .size();
    Optional<Account> accountDetails = accountService.retrieveAccountById(accountId);
    accountService.withdrawMoney(100, accountId);
    List<AccountStatement> accountStatementDetails = accountService.retrieveAccountStatementByAccountId(accountId);
    Assertions.assertThat(accountDetails.isPresent());
    Assertions.assertThat(accountDetails.get()
                                        .getId())
              .isEqualTo(accountId);
    Assertions.assertThat(accountDetails.get()
                                        .getCurrentBalance())
              .isEqualTo(100);

    Assertions.assertThat(accountStatementDetails.size())
              .isEqualTo(dataBaseSize + 1);
  }

  @Test
  public void retrieveAccountStatement_ThrowsException_ifAccountIdNull(){
    Exception exception = assertThrows(InvalidDataException.class, () -> {
      accountService.retrieveAccountStatement(null,null,null,1,2);
    });
    Exception exception_1 = assertThrows(InvalidDataException.class, () -> {
      accountService.retrieveAccountStatement("",null,null,1,2);
    });
    String expectedMessage = "Account Id must be provided";
    String actualMessage = exception.getMessage();
    String actualMessage_1 = exception_1.getMessage();
    assertTrue(actualMessage.contains(expectedMessage));
    assertTrue(actualMessage_1.contains(expectedMessage));
  }

  @Test
  public void retrieveAccountStatement_ThrowsException_ifPeriodNotValid(){
    ZonedDateTime startDate = ZonedDateTime.of(2021, 01, 01, 0, 0, 0, 0, ZoneId.of("UTC"));
    ZonedDateTime endDate = ZonedDateTime.of(2020, 01, 01, 0, 0, 0, 0, ZoneId.of("UTC"));
    Exception exception = assertThrows(InvalidDataException.class, () -> {
      accountService.retrieveAccountStatement(ACCOUNT_ID, startDate, endDate,1,2);
    });

    String expectedMessage = "The start date must be before the end date";
    String actualMessage = exception.getMessage();
    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  public void retrieveAccountStatement_ThrowsException_ifPageInformationInvalid(){

    Exception exception = assertThrows(InvalidDataException.class, () -> {
      accountService.retrieveAccountStatement(ACCOUNT_ID, null, null,-1,2);
    });

    Exception exception_1 = assertThrows(InvalidDataException.class, () -> {
      accountService.retrieveAccountStatement(ACCOUNT_ID, null, null,0,-2);
    });

    String expectedMessage = "Not Valid Page";
    String actualMessage = exception.getMessage();
    assertTrue(actualMessage.contains(expectedMessage));

    String expectedMessage_1 = "Not Valid nbElements";
    String actualMessage_1 = exception_1.getMessage();
    assertTrue(actualMessage_1.contains(expectedMessage_1));
  }
  @Test
  public void retrieveAccountStatement_inAllPeriod(){

    String accountId = UUID.randomUUID()
                           .toString();
    prepareAccountStatement(accountId);

    Page<AccountStatement> accountStatements = accountService.retrieveAccountStatement(accountId, null, null,0,10);
    assertEquals(accountStatements.getTotalElements(),6);
    assertEquals(accountStatements.getTotalPages(),1);
    assertEquals(accountStatements.getContent().size(),6);

    Page<AccountStatement> accountStatements_1 = accountService.retrieveAccountStatement(accountId, null, null,1,2);
    assertEquals(accountStatements_1.getTotalElements(),6);
    assertEquals(accountStatements_1.getTotalPages(),3);
    assertEquals(accountStatements_1.getContent().size(),2);

    Page<AccountStatement> accountStatements_2 = accountService.retrieveAccountStatement(accountId, null, null,1,10);
    assertEquals(accountStatements_2.getTotalElements(),6);
    assertEquals(accountStatements_2.getTotalPages(),1);
    assertEquals(accountStatements_2.getContent().size(),0);
  }

  @Test
  public void retrieveAccountStatement_withStartDate(){

    String accountId = UUID.randomUUID()
                           .toString();
    prepareAccountStatement(accountId);

    ZonedDateTime startDate = ZonedDateTime.of(2021, 01, 01, 0, 0, 0, 0, ZoneId.of("UTC"));
    ZonedDateTime startDate_1 = ZonedDateTime.of(2025, 01, 01, 0, 0, 0, 0, ZoneId.of("UTC"));

    Page<AccountStatement> accountStatements = accountService.retrieveAccountStatement(accountId, startDate, null,0,10);
    assertEquals(accountStatements.getTotalElements(),6);
    assertEquals(accountStatements.getTotalPages(),1);
    assertEquals(accountStatements.getContent().size(),6);

    Page<AccountStatement> accountStatements_1 = accountService.retrieveAccountStatement(accountId, startDate_1, null,0,10);
    assertEquals(accountStatements_1.getTotalElements(),0);
    assertEquals(accountStatements_1.getTotalPages(),0);
    assertEquals(accountStatements_1.getContent().size(),0);
  }

  @Test
  public void retrieveAccountStatement_withEndDate(){

    String accountId = UUID.randomUUID()
                           .toString();

    prepareAccountStatement(accountId);
    ZonedDateTime endDate = ZonedDateTime.of(2021, 01, 01, 0, 0, 0, 0, ZoneId.of("UTC"));
    ZonedDateTime endDate_1 = ZonedDateTime.of(2025, 01, 01, 0, 0, 0, 0, ZoneId.of("UTC"));

    Page<AccountStatement> accountStatements = accountService.retrieveAccountStatement(accountId, null, endDate,0,10);
    assertEquals(accountStatements.getTotalElements(),0);
    assertEquals(accountStatements.getTotalPages(),0);
    assertEquals(accountStatements.getContent().size(),0);

    Page<AccountStatement> accountStatements_1 = accountService.retrieveAccountStatement(accountId, null, endDate_1,0,10);
    assertEquals(accountStatements_1.getTotalElements(),6);
    assertEquals(accountStatements_1.getTotalPages(),1);
    assertEquals(accountStatements_1.getContent().size(),6);
  }

  @Test
  public void retrieveAccountStatement_withStartAndEndDate(){

    String accountId = UUID.randomUUID()
                           .toString();

    prepareAccountStatement(accountId);

    ZonedDateTime startDate = ZonedDateTime.of(2021, 01, 01, 0, 0, 0, 0, ZoneId.of("UTC"));
    ZonedDateTime endDate = ZonedDateTime.of(2025, 01, 01, 0, 0, 0, 0, ZoneId.of("UTC"));

    Page<AccountStatement> accountStatements = accountService.retrieveAccountStatement(accountId, startDate, endDate,0,10);
    assertEquals(accountStatements.getTotalElements(),6);
    assertEquals(accountStatements.getTotalPages(),1);
    assertEquals(accountStatements.getContent().size(),6);

    ZonedDateTime startDate_1 = ZonedDateTime.of(2023, 01, 01, 0, 0, 0, 0, ZoneId.of("UTC"));
    ZonedDateTime endDate_1 = ZonedDateTime.of(2025, 01, 01, 0, 0, 0, 0, ZoneId.of("UTC"));

    Page<AccountStatement> accountStatements_1 = accountService.retrieveAccountStatement(accountId, startDate_1, endDate_1,0,10);
    assertEquals(accountStatements_1.getTotalElements(),0);
    assertEquals(accountStatements_1.getTotalPages(),0);
    assertEquals(accountStatements_1.getContent().size(),0);

    ZonedDateTime startDate_2 = ZonedDateTime.of(2020, 01, 01, 0, 0, 0, 0, ZoneId.of("UTC"));
    ZonedDateTime endDate_2 = ZonedDateTime.of(2021, 01, 01, 0, 0, 0, 0, ZoneId.of("UTC"));

    Page<AccountStatement> accountStatements_2 = accountService.retrieveAccountStatement(accountId, startDate_2, endDate_2,0,10);
    assertEquals(accountStatements_2.getTotalElements(),0);
    assertEquals(accountStatements_2.getTotalPages(),0);
    assertEquals(accountStatements_2.getContent().size(),0);
  }

  /**
   * create account with many actions to save many account statement
   * @param accountId account id
   */
  private void prepareAccountStatement(String accountId){
    accountService.createAccount("147852", 200, accountId);
    accountService.depositMoney(100, accountId);
    accountService.depositMoney(100, accountId);
    accountService.depositMoney(100, accountId);
    accountService.depositMoney(100, accountId);
    accountService.withdrawMoney(100, accountId);
  }
}