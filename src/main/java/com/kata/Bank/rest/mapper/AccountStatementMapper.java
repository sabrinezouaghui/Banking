package com.kata.Bank.rest.mapper;

import com.kata.Bank.models.AccountStatement;
import com.kata.Bank.rest.response.AccountStatementResponse;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Account Statement mapper
 *
 * @author Sabrine.zouaghi
 */
@Mapper(componentModel = "spring")
public interface AccountStatementMapper {

  /**
   * Map Create AccountStatement Entity to DTO
   *
   * @param accountStatement accountStatement
   * @return {@link AccountStatementResponse}
   */
  AccountStatementResponse accountStatementEntityToDto(AccountStatement accountStatement);

  /**
   * Map Create AccountStatement Entity to DTO List
   *
   * @param accountStatement accountStatement
   * @return {@link List} of {@link AccountStatementResponse}
   */
  List<AccountStatementResponse> accountStatementEntityToDto(List<AccountStatement> accountStatement);
}