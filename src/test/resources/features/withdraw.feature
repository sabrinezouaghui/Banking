Feature: Withdraw Amount in each Account

   Scenario Outline: I'm client I want to withdraw negative Amount.
     Given I can not withdraw negative amount in account id <accountId>, amount <amount>
     Then  I got Exception "BadRequest"

     Examples:
     |accountId      |amount     |
     |123456         | -100      |
     |123456         | 0         |


  Scenario Outline: I'm client I want to withdraw money with account id null or empty.
    Given I can not withdraw money when account id <accountId> is empty, amount <amount>
    Then  I got Exception "BadRequest"

    Examples:
      |accountId      |amount     |
      |null           | 100       |
      |""           | 100       |


  Scenario Outline: I'm client I want to withdraw money with account id not exist.
    Given I can not withdraw money in account id <accountId> when not exist, amount <amount>
    Then  I got Exception "BadRequest"

    Examples:
      |accountId      |amount     |
      |123456         | 100       |

  Scenario Outline: I'm client I want to withdraw money in my account id.
    Given I withdraw money in my account id <accountId>, amount <amount>
    Then  My current Balance is 300

    Examples:
      |accountId      |amount     |
      |123456         | 100       |