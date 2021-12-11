package com.kata.Bank.BDD;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    plugin ={"pretty","json:target/cucumber.json"},
    features = {"classpath:features/deposit.feature","classpath:features/withdraw.feature","classpath:features/retrieveAccountStatement.feature"},
    glue={"com.kata.Bank.BDD.DepositStep","com.kata.Bank.BDD.WithdrawStep","com.kata.Bank.BDD.RetrieveAccountStatementStep"}
)
public class CucumberRunnerTest {
}
