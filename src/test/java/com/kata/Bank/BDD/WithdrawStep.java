package com.kata.Bank.BDD;

import com.kata.Bank.models.Account;
import com.kata.Bank.models.AccountStatement;
import com.kata.Bank.rest.request.DepositAmountRequest;
import com.kata.Bank.service.AccountService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

/**
 * @author Sabrine.zouaghi
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class WithdrawStep {

  private static final String uri = "http://localhost";
  private static final String WITHDRAW_URL = "/withdraw";
  private static final String ACCOUNT_URL = "/account";
  private static final String USER_ID = "user_id";

  @LocalServerPort
  private int port;

  @Autowired
  private AccountService accountService;

  private Exception actualException;
  private RestTemplate restTemplate = new RestTemplate();

  private String url() {
    return uri + ":" + port + ACCOUNT_URL + WITHDRAW_URL;
  }

  @Then("^I can not withdraw negative amount in account id (.*), amount (.*)$")
  public void i_cannot_withdraw_negative_amount(String accountId, int amount) {
    DepositAmountRequest request = DepositAmountRequest.builder()
                                                       .accountId(accountId)
                                                       .amount(amount)
                                                       .build();
    try {
      restTemplate.exchange(url(), HttpMethod.POST, new HttpEntity<>(request), Object.class);

    } catch (Exception e) {
      actualException = e;
      throw new io.cucumber.java.PendingException(e.getMessage());
    }

  }

  @Then("^I can not withdraw money when account id (.*) is empty, amount (.*)$")
  public void i_cannot_withdraw_amount_when_accountId_empty(String accountId, int amount) {
    DepositAmountRequest request = DepositAmountRequest.builder()
                                                       .accountId(accountId)
                                                       .amount(amount)
                                                       .build();
    try {
      restTemplate.exchange(url(), HttpMethod.POST, new HttpEntity<>(request), Object.class);
    } catch (Exception e) {
      actualException = e;
      throw new io.cucumber.java.PendingException(e.getMessage());
    }

  }

  @Then("^I can not withdraw money in account id (.*) when not exist, amount (.*)$")
  public void i_cannot_withdraw_amount_when_account_notExist(String accountId, int amount) {
    DepositAmountRequest request = DepositAmountRequest.builder()
                                                       .accountId(accountId)
                                                       .amount(amount)
                                                       .build();
    try {
      restTemplate.exchange(url(), HttpMethod.POST, new HttpEntity<>(request), Object.class);
    } catch (Exception e) {
      actualException = e;
      throw new io.cucumber.java.PendingException(e.getMessage());
    }

  }

  @Then("^I withdraw money in my account id (.*), amount (.*)$")
  public void i_withdraw_amount_in_my_account(String accountId, int amount) {

    accountService.createAccount(USER_ID, 200, accountId);
    int dataBaseSize = accountService.retrieveAccountStatementByAccountId(accountId)
                                     .size();
    DepositAmountRequest request = DepositAmountRequest.builder()
                                                       .accountId(accountId)
                                                       .amount(amount)
                                                       .build();

    ResponseEntity<?> responseEntity = restTemplate.exchange(url(), HttpMethod.POST, new HttpEntity<>(request), Object.class);
    Optional<Account> accountDetails = accountService.retrieveAccountById(accountId);
    List<AccountStatement> accountStatementDetails = accountService.retrieveAccountStatementByAccountId(accountId);

    Assertions.assertEquals(200, responseEntity.getStatusCodeValue());
    Assertions.assertEquals(accountDetails.get()
                                          .getCurrentBalance(), 100);
    Assertions.assertEquals(accountStatementDetails.size(), dataBaseSize + 1);
  }
}
