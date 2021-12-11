package com.kata.Bank.rest.controller;

import com.kata.Bank.exceptions.InvalidDataException;
import com.kata.Bank.rest.mapper.AccountStatementMapper;
import com.kata.Bank.service.AccountService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.BDDMockito.given;
/**
 * @author Sabrine.zouaghi
 */
@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private AccountService accountService;

  @MockBean
  private AccountStatementMapper accountStatementMapper;

  private static final String ACCOUNT_URL = "/account";
  private static final String DEPOSIT_URL = "/deposit";
  private static final String WITHDRAW_URL = "/withdraw";
  private static final String RETRIEVE_STATEMENT = ACCOUNT_URL + "/statement";

  @Test
  public void depositMoney_throw_whenAmountNegative() throws Exception {
    Mockito.doThrow(InvalidDataException.class).when(accountService)
           .depositMoney(anyInt(), anyString());
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ACCOUNT_URL + DEPOSIT_URL)
                                                                        .accept(MediaType.APPLICATION_JSON)
                                                                        .content("{\n" + "  \"accountId\": \"123456\",\n"
                                                                            + "  \"amount\": \"-100\"\n" + "}")
                                                                        .contentType(MediaType.APPLICATION_JSON));

    ResultActions resultActions_1 = mockMvc.perform(MockMvcRequestBuilders.post(ACCOUNT_URL + DEPOSIT_URL)
                                                                          .accept(MediaType.APPLICATION_JSON)
                                                                          .content("{\n" + "  \"accountId\": \"123456\",\n"
                                                                              + "  \"amount\": \"0\"\n" + "}")
                                                                          .contentType(MediaType.APPLICATION_JSON));
    resultActions.andExpect(MockMvcResultMatchers.status()
                                                 .is(400));

    resultActions_1.andExpect(MockMvcResultMatchers.status()
                                                   .is(400));

  }

  @Test
  public void depositMoney_throw_whenAccountIdNull() throws Exception {
    doThrow(InvalidDataException.class).when(accountService)
                                       .depositMoney(anyInt(), anyString());
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ACCOUNT_URL + DEPOSIT_URL)
                                                                        .accept(MediaType.APPLICATION_JSON)
                                                                        .content(
                                                                            "{\n" + "  \"accountId\": \"\",\n" + "  \"amount\": \"-100\",\n"
                                                                                + "}")
                                                                        .contentType(MediaType.APPLICATION_JSON));

    ResultActions resultActions_1 = mockMvc.perform(MockMvcRequestBuilders.post(ACCOUNT_URL + DEPOSIT_URL)
                                                                          .accept(MediaType.APPLICATION_JSON)
                                                                          .content("{\n" + "  \"accountId\": \"null\",\n"
                                                                              + "  \"amount\": \"0\",\n" + "}")
                                                                          .contentType(MediaType.APPLICATION_JSON));
    resultActions.andExpect(MockMvcResultMatchers.status()
                                                 .is(400));

    resultActions_1.andExpect(MockMvcResultMatchers.status()
                                                   .is(400));

  }

  @Test
  public void depositMoney_throw_whenAccountIdNotExist() throws Exception {

    doThrow(InvalidDataException.class).when(accountService)
                                       .depositMoney(anyInt(), anyString());

    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ACCOUNT_URL + DEPOSIT_URL)
                                                                        .accept(MediaType.APPLICATION_JSON)
                                                                        .content("{\n" + "  \"accountId\": \"123456\",\n"
                                                                            + "  \"amount\": \"100\"\n" + "}")
                                                                        .contentType(MediaType.APPLICATION_JSON));

    resultActions.andExpect(MockMvcResultMatchers.status()
                                                 .is(400));

  }

  @Test
  public void depositMoney() throws Exception {
    doNothing().when(accountService)
               .depositMoney(anyInt(), anyString());
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ACCOUNT_URL + DEPOSIT_URL)
                                                                        .accept(MediaType.APPLICATION_JSON)
                                                                        .content("{\n" + "  \"accountId\": \"123456\",\n"
                                                                            + "  \"amount\": \"100\"\n" + "}")
                                                                        .contentType(MediaType.APPLICATION_JSON));
    resultActions.andExpect(MockMvcResultMatchers.status()
                                                 .is(200));

  }

  @Test
  public void withdrawMoney_throw_whenAmountNegative() throws Exception {

    doThrow(InvalidDataException.class).when(accountService)
                                       .withdrawMoney(anyInt(), anyString());
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ACCOUNT_URL + WITHDRAW_URL)
                                                                        .accept(MediaType.APPLICATION_JSON)
                                                                        .content("{\n" + "  \"accountId\": \"123456\",\n"
                                                                            + "  \"amount\": \"-100\"\n" + "}")
                                                                        .contentType(MediaType.APPLICATION_JSON));

    ResultActions resultActions_1 = mockMvc.perform(MockMvcRequestBuilders.post(ACCOUNT_URL + WITHDRAW_URL)
                                                                          .accept(MediaType.APPLICATION_JSON)
                                                                          .content("{\n" + "  \"accountId\": \"123456\",\n"
                                                                              + "  \"amount\": \"0\"\n" + "}")
                                                                          .contentType(MediaType.APPLICATION_JSON));
    resultActions.andExpect(MockMvcResultMatchers.status()
                                                 .is(400));

    resultActions_1.andExpect(MockMvcResultMatchers.status()
                                                   .is(400));

  }

  @Test
  public void withdrawMoney_throw_whenAccountIdNull() throws Exception {

    doThrow(InvalidDataException.class).when(accountService)
                                       .withdrawMoney(anyInt(), anyString());
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ACCOUNT_URL + WITHDRAW_URL)
                                                                        .accept(MediaType.APPLICATION_JSON)
                                                                        .content(
                                                                            "{\n" + "  \"accountId\": \"\",\n" + "  \"amount\": \"-100\",\n"
                                                                                + "}")
                                                                        .contentType(MediaType.APPLICATION_JSON));

    ResultActions resultActions_1 = mockMvc.perform(MockMvcRequestBuilders.post(ACCOUNT_URL + WITHDRAW_URL)
                                                                          .accept(MediaType.APPLICATION_JSON)
                                                                          .content("{\n" + "  \"accountId\": \"null\",\n"
                                                                              + "  \"amount\": \"0\",\n" + "}")
                                                                          .contentType(MediaType.APPLICATION_JSON));
    resultActions.andExpect(MockMvcResultMatchers.status()
                                                 .is(400));

    resultActions_1.andExpect(MockMvcResultMatchers.status()
                                                   .is(400));

  }

  @Test
  public void withdrawMoney_throw_whenAccountIdNotExist() throws Exception {

    doThrow(InvalidDataException.class).when(accountService)
                                       .withdrawMoney(anyInt(), anyString());

    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ACCOUNT_URL + WITHDRAW_URL)
                                                                        .accept(MediaType.APPLICATION_JSON)
                                                                        .content("{\n" + "  \"accountId\": \"123456\",\n"
                                                                            + "  \"amount\": \"100\"\n" + "}")
                                                                        .contentType(MediaType.APPLICATION_JSON));

    resultActions.andExpect(MockMvcResultMatchers.status()
                                                 .is(400));

  }

  @Test
  public void withdrawMoney() throws Exception {

    doNothing().when(accountService)
               .withdrawMoney(anyInt(), anyString());
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ACCOUNT_URL + WITHDRAW_URL)
                                                                        .accept(MediaType.APPLICATION_JSON)
                                                                        .content("{\n" + "  \"accountId\": \"123456\",\n"
                                                                            + "  \"amount\": \"100\"\n" + "}")
                                                                        .contentType(MediaType.APPLICATION_JSON));
    resultActions.andExpect(MockMvcResultMatchers.status()
                                                 .is(200));

  }

  @Test
  public void retrieveAccountStatement() throws Exception {

    String accountId = UUID.randomUUID()
                           .toString();
    ZonedDateTime startDate = ZonedDateTime.of(2021, 01, 01, 0, 0, 0, 0, ZoneId.of("UTC"));
    ZonedDateTime endDate = ZonedDateTime.of(2025, 01, 01, 0, 0, 0, 0, ZoneId.of("UTC"));
    Pageable pageable = PageRequest.of(0, 10);

    doNothing().when(accountService)
               .withdrawMoney(anyInt(), anyString());

    given(accountService.retrieveAccountStatement(anyString(),any(),any(),anyInt(),anyInt())).willReturn(new PageImpl<>(new ArrayList<>(), pageable, 0));

    ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get(RETRIEVE_STATEMENT)
                                                                             .param("accountId", accountId)
                                                                             .param("page", "0").param("size", "10")
                                                                             .param("startDate", startDate.toString())
                                                                             .param("endDate", endDate.toString()));
    resultActions.andExpect(MockMvcResultMatchers.status()
                                                 .is(200));

  }
}
