package com.kata.Bank.rest.controller;

import com.kata.Bank.models.AccountStatement;
import com.kata.Bank.rest.mapper.AccountStatementMapper;
import com.kata.Bank.rest.request.DepositAmountRequest;
import com.kata.Bank.rest.request.RetrieveAccountStatementRequest;
import com.kata.Bank.rest.request.WithdrawAmountRequest;
import com.kata.Bank.rest.response.RetrieveAccountStatementResponse;
import com.kata.Bank.service.AccountService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Class Controller containing main methods to account management
 *
 * @author Sabrine.zouaghi
 */

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/account", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {

  private static final String STATEMENT_URL = "/statement";
  private static final String DEPOSIT_URL = "/deposit";
  private static final String WITHDRAW_URL = "/withdraw";

  /**
   * Account Service
   */
  private AccountService accountService;

  /**
   * Account Statement Mapper
   */
  private AccountStatementMapper accountStatementMapper;

  public AccountController(AccountService accountService, AccountStatementMapper accountStatementMapper) {
    this.accountService = accountService;
    this.accountStatementMapper = accountStatementMapper;
  }

  @GetMapping(STATEMENT_URL)
  public ResponseEntity<RetrieveAccountStatementResponse> retrieveAccountStatement(
      RetrieveAccountStatementRequest retrieveAccountStatementRequest) {

    Page<AccountStatement> accountStatementsResponse = accountService.retrieveAccountStatement(
        retrieveAccountStatementRequest.getAccountId(), retrieveAccountStatementRequest.getStartDate(),
        retrieveAccountStatementRequest.getEndDate(), retrieveAccountStatementRequest.getPage(), retrieveAccountStatementRequest.getSize());
    return ResponseEntity.status(HttpStatus.OK)
                         .body(RetrieveAccountStatementResponse.builder()
                                                               .accountStatement(accountStatementMapper.accountStatementEntityToDto(
                                                                   accountStatementsResponse.getContent()))
                                                               .totalPages(accountStatementsResponse.getTotalPages())
                                                               .totalElements(accountStatementsResponse.getTotalElements())
                                                               .build());
  }

  @PostMapping(DEPOSIT_URL)
  public ResponseEntity depositAmount(@Valid @RequestBody DepositAmountRequest request) {
    accountService.depositMoney(request.getAmount(), request.getAccountId());
    return ResponseEntity.ok()
                         .build();
  }

  @PostMapping(WITHDRAW_URL)
  public ResponseEntity withdrawAmount(@Valid @RequestBody WithdrawAmountRequest request) {
    accountService.withdrawMoney(request.getAmount(), request.getAccountId());
    return ResponseEntity.ok()
                         .build();
  }

}