package com.kata.Bank.rest.controller;

import com.kata.Bank.models.Account;
import com.kata.Bank.models.AccountStatement;
import com.kata.Bank.rest.request.DepositAmountRequest;
import com.kata.Bank.rest.response.RetrieveAccountStatementResponse;
import com.kata.Bank.service.AccountService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountControllerIT {

  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private AccountService accountService;

  private static final String ACCOUNT_URL = "/account";
  private static final String DEPOSIT_URL = "/deposit";
  private static final String WITHDRAW_URL = "/withdraw";
  private static final String RETRIEVE_STATEMENT = ACCOUNT_URL + "/statement";
  private static final String ACCOUNT = "account_id";
  private static final String BAD_REQUEST = "Bad Request";
  private static final String USER_ID = "user_id";
  private static final String ACCOUNT_ID = "accountId";
  private static final String PAGE = "page";
  private static final String SIZE = "size";
  private static final String END_DATE = "endDate";
  private static final String START_DATE = "startDate";

  @Test
  public void depositMoney_throw_whenAmountNegative() throws Exception {
    DepositAmountRequest request = DepositAmountRequest.builder()
                                                       .accountId(ACCOUNT)
                                                       .amount(-100)
                                                       .build();
    ResponseEntity<?> responseEntity = restTemplate.exchange(ACCOUNT_URL + DEPOSIT_URL, HttpMethod.POST, new HttpEntity<>(request),
        Object.class);

    DepositAmountRequest request_1 = DepositAmountRequest.builder()
                                                         .accountId(ACCOUNT)
                                                         .amount(0)
                                                         .build();
    ResponseEntity<?> responseEntity_1 = restTemplate.exchange(ACCOUNT_URL + DEPOSIT_URL, HttpMethod.POST, new HttpEntity<>(request_1),
        Object.class);

    Assertions.assertEquals(400, responseEntity.getStatusCodeValue());
    Assertions.assertEquals(BAD_REQUEST, responseEntity.getStatusCode()
                                                       .getReasonPhrase());

    Assertions.assertEquals(400, responseEntity_1.getStatusCodeValue());
    Assertions.assertEquals(BAD_REQUEST, responseEntity_1.getStatusCode()
                                                         .getReasonPhrase());
  }

  @Test
  public void depositMoney_throw_whenAccountIdNull() throws Exception {
    DepositAmountRequest request = DepositAmountRequest.builder()
                                                       .accountId(null)
                                                       .amount(100)
                                                       .build();
    ResponseEntity<?> responseEntity = restTemplate.exchange(ACCOUNT_URL + DEPOSIT_URL, HttpMethod.POST, new HttpEntity<>(request),
        Object.class);

    DepositAmountRequest request_1 = DepositAmountRequest.builder()
                                                         .accountId("")
                                                         .amount(100)
                                                         .build();
    ResponseEntity<?> responseEntity_1 = restTemplate.exchange(ACCOUNT_URL + DEPOSIT_URL, HttpMethod.POST, new HttpEntity<>(request_1),
        Object.class);

    Assertions.assertEquals(400, responseEntity.getStatusCodeValue());
    Assertions.assertEquals(BAD_REQUEST, responseEntity.getStatusCode()
                                                       .getReasonPhrase());

    Assertions.assertEquals(400, responseEntity_1.getStatusCodeValue());
    Assertions.assertEquals(BAD_REQUEST, responseEntity_1.getStatusCode()
                                                         .getReasonPhrase());
  }

  @Test
  public void depositMoney_throw_whenAccountIdNotExist() throws Exception {
    DepositAmountRequest request = DepositAmountRequest.builder()
                                                       .accountId(ACCOUNT)
                                                       .amount(100)
                                                       .build();
    ResponseEntity<?> responseEntity = restTemplate.exchange(ACCOUNT_URL + DEPOSIT_URL, HttpMethod.POST, new HttpEntity<>(request),
        Object.class);

    Assertions.assertEquals(400, responseEntity.getStatusCodeValue());
    Assertions.assertEquals(BAD_REQUEST, responseEntity.getStatusCode()
                                                       .getReasonPhrase());
  }

  @Test
  public void depositMoney() throws Exception {
    String accountId = UUID.randomUUID()
                           .toString();
    accountService.createAccount(USER_ID, 200, accountId);
    int dataBaseSize = accountService.retrieveAccountStatementByAccountId(accountId)
                                     .size();
    DepositAmountRequest request = DepositAmountRequest.builder()
                                                       .accountId(accountId)
                                                       .amount(100)
                                                       .build();
    ResponseEntity<?> responseEntity = restTemplate.exchange(ACCOUNT_URL + DEPOSIT_URL, HttpMethod.POST, new HttpEntity<>(request),
        Object.class);

    Optional<Account> accountDetails = accountService.retrieveAccountById(accountId);
    List<AccountStatement> accountStatementDetails = accountService.retrieveAccountStatementByAccountId(accountId);

    Assertions.assertEquals(200, responseEntity.getStatusCodeValue());
    Assertions.assertEquals(accountDetails.get()
                                          .getCurrentBalance(), 300);
    Assertions.assertEquals(accountStatementDetails.size(), dataBaseSize + 1);
  }

  @Test
  public void withdrawMoney_throw_whenAmountNegative() throws Exception {
    DepositAmountRequest request = DepositAmountRequest.builder()
                                                       .accountId(ACCOUNT)
                                                       .amount(-100)
                                                       .build();
    ResponseEntity<?> responseEntity = restTemplate.exchange(ACCOUNT_URL + WITHDRAW_URL, HttpMethod.POST, new HttpEntity<>(request),
        Object.class);

    DepositAmountRequest request_1 = DepositAmountRequest.builder()
                                                         .accountId(ACCOUNT)
                                                         .amount(0)
                                                         .build();
    ResponseEntity<?> responseEntity_1 = restTemplate.exchange(ACCOUNT_URL + WITHDRAW_URL, HttpMethod.POST, new HttpEntity<>(request_1),
        Object.class);

    Assertions.assertEquals(400, responseEntity.getStatusCodeValue());
    Assertions.assertEquals(BAD_REQUEST, responseEntity.getStatusCode()
                                                       .getReasonPhrase());

    Assertions.assertEquals(400, responseEntity_1.getStatusCodeValue());
    Assertions.assertEquals(BAD_REQUEST, responseEntity_1.getStatusCode()
                                                         .getReasonPhrase());
  }

  @Test
  public void withdrawMoney_throw_whenAccountIdNull() throws Exception {
    DepositAmountRequest request = DepositAmountRequest.builder()
                                                       .accountId(null)
                                                       .amount(100)
                                                       .build();
    ResponseEntity<?> responseEntity = restTemplate.exchange(ACCOUNT_URL + WITHDRAW_URL, HttpMethod.POST, new HttpEntity<>(request),
        Object.class);

    DepositAmountRequest request_1 = DepositAmountRequest.builder()
                                                         .accountId("")
                                                         .amount(100)
                                                         .build();
    ResponseEntity<?> responseEntity_1 = restTemplate.exchange(ACCOUNT_URL + WITHDRAW_URL, HttpMethod.POST, new HttpEntity<>(request_1),
        Object.class);

    Assertions.assertEquals(400, responseEntity.getStatusCodeValue());
    Assertions.assertEquals(BAD_REQUEST, responseEntity.getStatusCode()
                                                       .getReasonPhrase());

    Assertions.assertEquals(400, responseEntity_1.getStatusCodeValue());
    Assertions.assertEquals(BAD_REQUEST, responseEntity_1.getStatusCode()
                                                         .getReasonPhrase());
  }

  @Test
  public void withdrawMoney_throw_whenAccountIdNotExist() throws Exception {
    DepositAmountRequest request = DepositAmountRequest.builder()
                                                       .accountId(ACCOUNT)
                                                       .amount(100)
                                                       .build();
    ResponseEntity<?> responseEntity = restTemplate.exchange(ACCOUNT_URL + WITHDRAW_URL, HttpMethod.POST, new HttpEntity<>(request),
        Object.class);

    Assertions.assertEquals(400, responseEntity.getStatusCodeValue());
    Assertions.assertEquals(BAD_REQUEST, responseEntity.getStatusCode()
                                                       .getReasonPhrase());
  }

  @Test
  public void withdrawMoney() throws Exception {
    String accountId = UUID.randomUUID()
                           .toString();
    accountService.createAccount(USER_ID, 200, accountId);
    int dataBaseSize = accountService.retrieveAccountStatementByAccountId(accountId)
                                     .size();
    DepositAmountRequest request = DepositAmountRequest.builder()
                                                       .accountId(accountId)
                                                       .amount(100)
                                                       .build();
    ResponseEntity<?> responseEntity = restTemplate.exchange(ACCOUNT_URL + WITHDRAW_URL, HttpMethod.POST, new HttpEntity<>(request),
        Object.class);

    Optional<Account> accountDetails = accountService.retrieveAccountById(accountId);
    List<AccountStatement> accountStatementDetails = accountService.retrieveAccountStatementByAccountId(accountId);

    Assertions.assertEquals(200, responseEntity.getStatusCodeValue());
    Assertions.assertEquals(accountDetails.get()
                                          .getCurrentBalance(), 100);
    Assertions.assertEquals(accountStatementDetails.size(), dataBaseSize + 1);
  }

  @Test
  public void retrieveAccountStatement_throw_whenAccountIdNull() throws Exception {

    HttpHeaders headers = new HttpHeaders();
    headers.set("Accept", "application/json");

    Map<String, String> params = new HashMap<>();
    params.put(PAGE, "0");
    params.put(SIZE, "10");

    String urlTemplate = UriComponentsBuilder.fromUriString(RETRIEVE_STATEMENT)
                                             .queryParam(PAGE, "{page}")
                                             .queryParam(SIZE, "{size}")
                                             .encode()
                                             .toUriString();

    HttpEntity entity = new HttpEntity(headers);

    ResponseEntity<?> responseEntity = restTemplate.exchange(urlTemplate, HttpMethod.GET, entity, Object.class, params);

    Assertions.assertEquals(400, responseEntity.getStatusCodeValue());
    Assertions.assertEquals(BAD_REQUEST, responseEntity.getStatusCode()
                                                       .getReasonPhrase());
  }

  @Test
  public void retrieveAccountStatement_throw_whenPeriodNotValid() throws Exception {

    ZonedDateTime startDate = ZonedDateTime.of(2021, 01, 01, 0, 0, 0, 0, ZoneId.of("UTC"));
    ZonedDateTime endDate = ZonedDateTime.of(2020, 01, 01, 0, 0, 0, 0, ZoneId.of("UTC"));

    HttpHeaders headers = new HttpHeaders();
    headers.set("Accept", "application/json");

    Map<String, Object> params = new HashMap<>();
    params.put(ACCOUNT_ID, ACCOUNT);
    params.put(PAGE, "0");
    params.put(SIZE, "10");
    params.put(START_DATE, startDate);
    params.put(END_DATE, endDate);
    String urlTemplate = UriComponentsBuilder.fromUriString(RETRIEVE_STATEMENT)
                                             .queryParam(ACCOUNT_ID, "{accountId}")
                                             .queryParam(SIZE, "{size}")
                                             .queryParam(PAGE, "{page}")
                                             .queryParam(START_DATE, "{startDate}")
                                             .queryParam(END_DATE, "{endDate}")
                                             .encode()
                                             .toUriString();

    HttpEntity entity = new HttpEntity(headers);

    ResponseEntity<?> responseEntity = restTemplate.exchange(urlTemplate, HttpMethod.GET, entity, Object.class, params);

    Assertions.assertEquals(400, responseEntity.getStatusCodeValue());
    Assertions.assertEquals(BAD_REQUEST, responseEntity.getStatusCode()
                                                       .getReasonPhrase());

  }

  @Test
  public void retrieveAccountStatement_throw_PageInformationInvalid() throws Exception {
    HttpHeaders headers = new HttpHeaders();
    headers.set("Accept", "application/json");

    Map<String, Object> params = new HashMap<>();
    params.put(ACCOUNT_ID, ACCOUNT);
    params.put(PAGE, "-1");
    params.put(SIZE, "10");
    String urlTemplate = UriComponentsBuilder.fromUriString(RETRIEVE_STATEMENT)
                                             .queryParam(ACCOUNT_ID, "{accountId}")
                                             .queryParam(SIZE, "{size}")
                                             .queryParam(PAGE, "{page}")
                                             .encode()
                                             .toUriString();

    HttpEntity entity = new HttpEntity(headers);

    ResponseEntity<?> responseEntity = restTemplate.exchange(urlTemplate, HttpMethod.GET, entity, Object.class, params);

    Map<String, Object> params_1 = new HashMap<>();
    params_1.put(ACCOUNT_ID, ACCOUNT);
    params_1.put(PAGE, "1");
    params_1.put(SIZE, "-10");

    ResponseEntity<?> responseEntity_1 = restTemplate.exchange(urlTemplate, HttpMethod.GET, entity, Object.class, params_1);

    Assertions.assertEquals(400, responseEntity.getStatusCodeValue());
    Assertions.assertEquals(BAD_REQUEST, responseEntity.getStatusCode()
                                                       .getReasonPhrase());
    Assertions.assertEquals(400, responseEntity_1.getStatusCodeValue());
    Assertions.assertEquals(BAD_REQUEST, responseEntity_1.getStatusCode()
                                                         .getReasonPhrase());
  }

  @Test
  public void retrieveAccountStatement_inAllPeriod() throws Exception {

    String accountId = UUID.randomUUID()
                           .toString();
    prepareAccountStatement(accountId);

    HttpHeaders headers = new HttpHeaders();
    headers.set("Accept", "application/json");
    HttpEntity entity = new HttpEntity(headers);

    String urlTemplate = UriComponentsBuilder.fromUriString(RETRIEVE_STATEMENT)
                                             .queryParam(ACCOUNT_ID, "{accountId}")
                                             .queryParam(PAGE, "{page}")
                                             .queryParam(SIZE, "{size}")
                                             .encode()
                                             .toUriString();

    Map<String, String> params = new HashMap<>();
    params.put(ACCOUNT_ID, accountId);
    params.put(PAGE, "0");
    params.put(SIZE, "10");

    ResponseEntity<RetrieveAccountStatementResponse> responseEntity = restTemplate.exchange(urlTemplate, HttpMethod.GET, entity,
        RetrieveAccountStatementResponse.class, params);

    Assertions.assertEquals(200, responseEntity.getStatusCodeValue());
    Assertions.assertEquals(6, responseEntity.getBody()
                                             .getTotalElements());
    Assertions.assertEquals(1, responseEntity.getBody()
                                             .getTotalPages());
    Assertions.assertEquals(6, responseEntity.getBody()
                                             .getAccountStatement()
                                             .size());

    Map<String, String> params_1 = new HashMap<>();
    params_1.put(ACCOUNT_ID, accountId);
    params_1.put(PAGE, "1");
    params_1.put(SIZE, "2");

    ResponseEntity<RetrieveAccountStatementResponse> responseEntity_1 = restTemplate.exchange(urlTemplate, HttpMethod.GET, entity,
        RetrieveAccountStatementResponse.class, params_1);

    Assertions.assertEquals(200, responseEntity_1.getStatusCodeValue());
    Assertions.assertEquals(6, responseEntity_1.getBody()
                                               .getTotalElements());
    Assertions.assertEquals(3, responseEntity_1.getBody()
                                               .getTotalPages());
    Assertions.assertEquals(2, responseEntity_1.getBody()
                                               .getAccountStatement()
                                               .size());

    Map<String, String> params_2 = new HashMap<>();
    params_2.put(ACCOUNT_ID, accountId);
    params_2.put(PAGE, "1");
    params_2.put(SIZE, "10");

    ResponseEntity<RetrieveAccountStatementResponse> responseEntity_2 = restTemplate.exchange(urlTemplate, HttpMethod.GET, entity,
        RetrieveAccountStatementResponse.class, params_2);

    Assertions.assertEquals(200, responseEntity_2.getStatusCodeValue());
    Assertions.assertEquals(6, responseEntity_2.getBody()
                                               .getTotalElements());
    Assertions.assertEquals(1, responseEntity_2.getBody()
                                               .getTotalPages());
    Assertions.assertEquals(0, responseEntity_2.getBody()
                                               .getAccountStatement()
                                               .size());
  }

  @Test
  public void retrieveAccountStatement_withStartDate() throws Exception {

    String accountId = UUID.randomUUID()
                           .toString();
    prepareAccountStatement(accountId);

    HttpHeaders headers = new HttpHeaders();
    headers.set("Accept", "application/json");
    HttpEntity entity = new HttpEntity(headers);

    String urlTemplate = UriComponentsBuilder.fromUriString(RETRIEVE_STATEMENT)
                                             .queryParam(ACCOUNT_ID, "{accountId}")
                                             .queryParam(PAGE, "{page}")
                                             .queryParam(SIZE, "{size}")
                                             .queryParam(START_DATE, "{startDate}")
                                             .encode()
                                             .toUriString();

    ZonedDateTime startDate = ZonedDateTime.of(2021, 01, 01, 0, 0, 0, 0, ZoneId.of("UTC"));
    Map<String, Object> params = new HashMap<>();
    params.put(ACCOUNT_ID, accountId);
    params.put(PAGE, "0");
    params.put(SIZE, "10");
    params.put(START_DATE, startDate);

    ResponseEntity<RetrieveAccountStatementResponse> responseEntity = restTemplate.exchange(urlTemplate, HttpMethod.GET, entity,
        RetrieveAccountStatementResponse.class, params);

    Assertions.assertEquals(200, responseEntity.getStatusCodeValue());
    Assertions.assertEquals(6, responseEntity.getBody()
                                             .getTotalElements());
    Assertions.assertEquals(1, responseEntity.getBody()
                                             .getTotalPages());
    Assertions.assertEquals(6, responseEntity.getBody()
                                             .getAccountStatement()
                                             .size());

    ZonedDateTime startDate_1 = ZonedDateTime.of(2025, 01, 01, 0, 0, 0, 0, ZoneId.of("UTC"));
    Map<String, Object> params_1 = new HashMap<>();
    params_1.put(ACCOUNT_ID, accountId);
    params_1.put(PAGE, "0");
    params_1.put(SIZE, "10");
    params_1.put(START_DATE, startDate_1);

    ResponseEntity<RetrieveAccountStatementResponse> responseEntity_1 = restTemplate.exchange(urlTemplate, HttpMethod.GET, entity,
        RetrieveAccountStatementResponse.class, params_1);

    Assertions.assertEquals(200, responseEntity_1.getStatusCodeValue());
    Assertions.assertEquals(0, responseEntity_1.getBody()
                                               .getTotalElements());
    Assertions.assertEquals(0, responseEntity_1.getBody()
                                               .getTotalPages());
    Assertions.assertEquals(0, responseEntity_1.getBody()
                                               .getAccountStatement()
                                               .size());

  }

  @Test
  public void retrieveAccountStatement_withEndDate() throws Exception {

    String accountId = UUID.randomUUID()
                           .toString();
    prepareAccountStatement(accountId);

    HttpHeaders headers = new HttpHeaders();
    headers.set("Accept", "application/json");
    HttpEntity entity = new HttpEntity(headers);

    String urlTemplate = UriComponentsBuilder.fromUriString(RETRIEVE_STATEMENT)
                                             .queryParam(ACCOUNT_ID, "{accountId}")
                                             .queryParam(PAGE, "{page}")
                                             .queryParam(SIZE, "{size}")
                                             .queryParam(END_DATE, "{endDate}")
                                             .encode()
                                             .toUriString();

    ZonedDateTime endDate = ZonedDateTime.of(2021, 01, 01, 0, 0, 0, 0, ZoneId.of("UTC"));
    Map<String, Object> params = new HashMap<>();
    params.put(ACCOUNT_ID, accountId);
    params.put(PAGE, "0");
    params.put(SIZE, "10");
    params.put(END_DATE, endDate);

    ResponseEntity<RetrieveAccountStatementResponse> responseEntity = restTemplate.exchange(urlTemplate, HttpMethod.GET, entity,
        RetrieveAccountStatementResponse.class, params);

    Assertions.assertEquals(200, responseEntity.getStatusCodeValue());
    Assertions.assertEquals(0, responseEntity.getBody()
                                             .getTotalElements());
    Assertions.assertEquals(0, responseEntity.getBody()
                                             .getTotalPages());
    Assertions.assertEquals(0, responseEntity.getBody()
                                             .getAccountStatement()
                                             .size());

    ZonedDateTime endDate_1 = ZonedDateTime.of(2025, 01, 01, 0, 0, 0, 0, ZoneId.of("UTC"));
    Map<String, Object> params_1 = new HashMap<>();
    params_1.put(ACCOUNT_ID, accountId);
    params_1.put(PAGE, "0");
    params_1.put(SIZE, "10");
    params_1.put(END_DATE, endDate_1);

    ResponseEntity<RetrieveAccountStatementResponse> responseEntity_1 = restTemplate.exchange(urlTemplate, HttpMethod.GET, entity,
        RetrieveAccountStatementResponse.class, params_1);

    Assertions.assertEquals(200, responseEntity_1.getStatusCodeValue());
    Assertions.assertEquals(6, responseEntity_1.getBody()
                                               .getTotalElements());
    Assertions.assertEquals(1, responseEntity_1.getBody()
                                               .getTotalPages());
    Assertions.assertEquals(6, responseEntity_1.getBody()
                                               .getAccountStatement()
                                               .size());

  }

  @Test
  public void retrieveAccountStatement_withStartAndEndDate() throws Exception {

    String accountId = UUID.randomUUID()
                           .toString();

    prepareAccountStatement(accountId);

    HttpHeaders headers = new HttpHeaders();
    headers.set("Accept", "application/json");
    HttpEntity entity = new HttpEntity(headers);

    String urlTemplate = UriComponentsBuilder.fromUriString(RETRIEVE_STATEMENT)
                                             .queryParam(ACCOUNT_ID, "{accountId}")
                                             .queryParam(PAGE, "{page}")
                                             .queryParam(SIZE, "{size}")
                                             .queryParam(START_DATE, "{startDate}")
                                             .queryParam(END_DATE, "{endDate}")
                                             .encode()
                                             .toUriString();

    ZonedDateTime startDate = ZonedDateTime.of(2021, 01, 01, 0, 0, 0, 0, ZoneId.of("UTC"));
    ZonedDateTime endDate = ZonedDateTime.of(2025, 01, 01, 0, 0, 0, 0, ZoneId.of("UTC"));

    Map<String, Object> params = new HashMap<>();
    params.put(ACCOUNT_ID, accountId);
    params.put(PAGE, "0");
    params.put(SIZE, "10");
    params.put(END_DATE, endDate);
    params.put(START_DATE, startDate);
    ResponseEntity<RetrieveAccountStatementResponse> responseEntity = restTemplate.exchange(urlTemplate, HttpMethod.GET, entity,
        RetrieveAccountStatementResponse.class, params);

    Assertions.assertEquals(200, responseEntity.getStatusCodeValue());
    Assertions.assertEquals(6, responseEntity.getBody()
                                             .getTotalElements());
    Assertions.assertEquals(1, responseEntity.getBody()
                                             .getTotalPages());
    Assertions.assertEquals(6, responseEntity.getBody()
                                             .getAccountStatement()
                                             .size());

    ZonedDateTime startDate_1 = ZonedDateTime.of(2023, 01, 01, 0, 0, 0, 0, ZoneId.of("UTC"));
    ZonedDateTime endDate_1 = ZonedDateTime.of(2025, 01, 01, 0, 0, 0, 0, ZoneId.of("UTC"));

    Map<String, Object> params_1 = new HashMap<>();
    params_1.put(ACCOUNT_ID, accountId);
    params_1.put(PAGE, "0");
    params_1.put(SIZE, "10");
    params_1.put(END_DATE, endDate_1);
    params_1.put(START_DATE, startDate_1);

    ResponseEntity<RetrieveAccountStatementResponse> responseEntity_1 = restTemplate.exchange(urlTemplate, HttpMethod.GET, entity,
        RetrieveAccountStatementResponse.class, params_1);

    Assertions.assertEquals(200, responseEntity_1.getStatusCodeValue());
    Assertions.assertEquals(0, responseEntity_1.getBody()
                                               .getTotalElements());
    Assertions.assertEquals(0, responseEntity_1.getBody()
                                               .getTotalPages());
    Assertions.assertEquals(0, responseEntity_1.getBody()
                                               .getAccountStatement()
                                               .size());

    ZonedDateTime startDate_2 = ZonedDateTime.of(2020, 01, 01, 0, 0, 0, 0, ZoneId.of("UTC"));
    ZonedDateTime endDate_2 = ZonedDateTime.of(2021, 01, 01, 0, 0, 0, 0, ZoneId.of("UTC"));

    Map<String, Object> params_2 = new HashMap<>();
    params_2.put(ACCOUNT_ID, accountId);
    params_2.put(PAGE, "0");
    params_2.put(SIZE, "10");
    params_2.put(END_DATE, endDate_2);
    params_2.put(START_DATE, startDate_2);

    ResponseEntity<RetrieveAccountStatementResponse> responseEntity_2 = restTemplate.exchange(urlTemplate, HttpMethod.GET, entity,
        RetrieveAccountStatementResponse.class, params_2);

    Assertions.assertEquals(200, responseEntity_2.getStatusCodeValue());
    Assertions.assertEquals(0, responseEntity_2.getBody()
                                               .getTotalElements());
    Assertions.assertEquals(0, responseEntity_2.getBody()
                                               .getTotalPages());
    Assertions.assertEquals(0, responseEntity_2.getBody()
                                               .getAccountStatement()
                                               .size());

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