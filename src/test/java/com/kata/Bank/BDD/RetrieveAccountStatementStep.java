package com.kata.Bank.BDD;

import com.kata.Bank.rest.response.RetrieveAccountStatementResponse;
import com.kata.Bank.service.AccountService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Sabrine.zouaghi
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RetrieveAccountStatementStep {

  private static final String uri = "http://localhost";
  private static final String STATEMENT_URL = "/statement";
  private static final String ACCOUNT_URL = "/account";
  private static final String USER_ID = "user_id";
  private static final String PAGE = "page";
  private static final String SIZE = "size";
  private static final String END_DATE = "endDate";
  private static final String START_DATE = "startDate";
  private static final String RETRIEVE_STATEMENT = ACCOUNT_URL + "/statement";
  private static final String ACCOUNT = "account_id";
  private static final String ACCOUNT_ID = "accountId";

  @LocalServerPort
  private int port;

  @Autowired
  private AccountService accountService;

  private Exception actualException;
  private RestTemplate restTemplate = new RestTemplate();

  private String url() {
    return uri + ":" + port + ACCOUNT_URL + STATEMENT_URL;
  }

  @Then("^I can not retrieve account statement when account id (.*) is null$")
  public void i_cannot_retrieve_account_statement(String accountId) {
    HttpHeaders headers = new HttpHeaders();
    headers.set("Accept", "application/json");

    Map<String, String> params = new HashMap<>();
    params.put(PAGE, "0");
    params.put(SIZE, "10");

    String urlTemplate = UriComponentsBuilder.fromUriString(url())
                                             .queryParam(PAGE, "{page}")
                                             .queryParam(SIZE, "{size}")
                                             .encode()
                                             .toUriString();

    HttpEntity entity = new HttpEntity(headers);
    try {

      restTemplate.exchange(urlTemplate, HttpMethod.GET, entity, Object.class, params);

    } catch (Exception e) {
      actualException = e;
      throw new io.cucumber.java.PendingException(e.getMessage());
    }

  }

  @Given("^I can not retrieve account statement when account end date is before start date, account id (.*), start date (.*), end date (.*)$")
  public void i_cannot_retrieve_accountStatement_when_duration_invalid(String accountId, String startDate, String endDate) {
    HttpHeaders headers = new HttpHeaders();
    headers.set("Accept", "application/json");
    HttpEntity entity = new HttpEntity(headers);
    Map<String, Object> params = new HashMap<>();
    params.put(ACCOUNT_ID, accountId);
    params.put(PAGE, "0");
    params.put(SIZE, "10");
    params.put(START_DATE, startDate);
    params.put(END_DATE, endDate);

    String urlTemplate = UriComponentsBuilder.fromUriString(url())
                                             .queryParam(ACCOUNT_ID, "{accountId}")
                                             .queryParam(SIZE, "{size}")
                                             .queryParam(PAGE, "{page}")
                                             .queryParam(START_DATE, "{startDate}")
                                             .queryParam(END_DATE, "{endDate}")
                                             .encode()
                                             .toUriString();

    try {

      restTemplate.exchange(urlTemplate, HttpMethod.GET, entity, Object.class, params);

    } catch (Exception e) {
      actualException = e;
      throw new io.cucumber.java.PendingException(e.getMessage());
    }

  }

  @Given("^I can not retrieve account statement when page or size invalid, account id (.*), page (.*), size (.*)$")
  public void i_cannot_retrieve_accountStatement_when_page_information_invalid(String accountId, String page, String size) {
    HttpHeaders headers = new HttpHeaders();
    headers.set("Accept", "application/json");
    HttpEntity entity = new HttpEntity(headers);
    Map<String, Object> params = new HashMap<>();
    params.put(ACCOUNT_ID, accountId);
    params.put(PAGE, page);
    params.put(SIZE, size);

    String urlTemplate = UriComponentsBuilder.fromUriString(url())
                                             .queryParam(ACCOUNT_ID, "{accountId}")
                                             .queryParam(SIZE, "{size}")
                                             .queryParam(PAGE, "{page}")
                                             .encode()
                                             .toUriString();

    try {

      restTemplate.exchange(urlTemplate, HttpMethod.GET, entity, Object.class, params);

    } catch (Exception e) {
      actualException = e;
      throw new io.cucumber.java.PendingException(e.getMessage());
    }

  }

  @Given("^I retrieve my account statement, account id (.*), page (.*), size (.*), start date (.*), end date (.*)$")
  public void i_retrieve_account_statement(String accountId, String page, String size, String startDate, String endDate) {
    prepareAccountStatement(accountId);
    HttpHeaders headers = new HttpHeaders();
    headers.set("Accept", "application/json");
    HttpEntity entity = new HttpEntity(headers);
    Map<String, Object> params = new HashMap<>();
    params.put(ACCOUNT_ID, accountId);
    params.put(PAGE, page);
    params.put(SIZE, size);

    UriComponentsBuilder uriParams = UriComponentsBuilder.fromUriString(url())
                                                         .queryParam(ACCOUNT_ID, "{accountId}")
                                                         .queryParam(SIZE, "{size}")
                                                         .queryParam(PAGE, "{page}");
    if (!ObjectUtils.isEmpty(startDate)) {
      params.put(START_DATE, startDate);
      uriParams.queryParam(START_DATE, "{startDate}");
    }

    if (!ObjectUtils.isEmpty(endDate)) {
      params.put(END_DATE, endDate);
      uriParams.queryParam(END_DATE, "{endDate}");
    }

    String urlTemplate = uriParams.encode()
                                  .toUriString();

    ResponseEntity<RetrieveAccountStatementResponse> responseEntity_2 = restTemplate.exchange(urlTemplate, HttpMethod.GET, entity,
        RetrieveAccountStatementResponse.class, params);

    Assertions.assertEquals(200, responseEntity_2.getStatusCodeValue());
  }

  /**
   * create account with many actions to save many account statement
   *
   * @param accountId account id
   */
  private void prepareAccountStatement(String accountId) {
    accountService.createAccount(USER_ID, 200, accountId);
    accountService.depositMoney(100, accountId);
    accountService.depositMoney(100, accountId);
    accountService.depositMoney(100, accountId);
    accountService.depositMoney(100, accountId);
    accountService.withdrawMoney(100, accountId);
  }
}
