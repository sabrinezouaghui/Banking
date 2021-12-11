package com.kata.Bank.rest.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Response Retrieve Account Statement
 *
 * @author Sabrine.zouaghi
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RetrieveAccountStatementResponse {
  /**
   * List of account statements
   */
  List<AccountStatementResponse> accountStatement;

  /**
   * Total pages
   */
  int totalPages;

  /**
   * Total elements
   */
  long totalElements;
}