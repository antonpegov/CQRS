package com.cqrs.account.command;

import com.cqrs.account.command.api.OpenAccountCommand;
import com.cqrs.account.command.domain.AccountAggregate;
import com.cqrs.account.common.dto.AccountType;
import com.cqrs.account.common.events.AccountClosedEvent;
import com.cqrs.account.common.events.AccountOpenedEvent;
import com.cqrs.account.common.events.FundsDepositedEvent;
import com.cqrs.account.common.events.FundsWithdrownEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

@DisplayName("Test AccountAggregate")
public class AccountAggregateTest {

    private AccountAggregate account;

    @BeforeEach
    public void setUp() {
        account = new AccountAggregate(new OpenAccountCommand());
    }

    @Test
    @DisplayName("Test apply method for AccountOpenedEvent")
    public void testApplyAccountOpenedEvent() {
        AccountOpenedEvent event = AccountOpenedEvent.builder()
                .id("123")
                .accountHolder("John Doe")
                .openingBalance(100.0)
                .accountType(AccountType.CURRENT)
                .createdDate(new Date())
                .build();

        account.apply(event);

        Assertions.assertEquals("123", account.getId());
        Assertions.assertTrue(account.isActive());
        Assertions.assertEquals(100.0, account.getBalance());
    }

    @Test
    @DisplayName("Test apply method for FundsDepositedEvent")
    public void testApplyFundsDepositedEvent() {
        account.setBalance(100.0);

        FundsDepositedEvent event = FundsDepositedEvent.builder()
                .id("123")
                .amount(50.0)
                .build();

        account.apply(event);

        Assertions.assertEquals(150.0, account.getBalance());
    }

    @Test
    @DisplayName("Test apply method for AccountClosedEvent")
    public void testApplyAccountClosedEvent() {
        account.setActive(true);

        AccountClosedEvent event = AccountClosedEvent.builder()
                .id("123")
                .build();

        account.apply(event);

        Assertions.assertFalse(account.isActive());
    }

    @Test
    @DisplayName("Test apply method for FundsWithdrawnEvent")
    public void testApplyFundsWithdrawnEvent() {
        account.setBalance(100.0);

        FundsWithdrownEvent event = FundsWithdrownEvent.builder()
                .id("123")
                .amount(50.0)
                .build();

        account.apply(event);

        Assertions.assertEquals(50.0, account.getBalance());
    }

    @Test
    @DisplayName("Test depositFunds method")
    public void testDepositFunds() {
        account.setActive(true);

        account.depositFunds(100.0);

        Assertions.assertEquals(100.0, account.getBalance());
    }

    @Test
    @DisplayName("Test withdrawFunds method")
    public void testWithdrawFunds() {
        account.setActive(true);
        account.setBalance(100.0);

        account.withdrawFunds(50.0);

        Assertions.assertEquals(50.0, account.getBalance());
    }

    @Test
    @DisplayName("Test closeAccount method")
    public void testCloseAccount() {
        account.setActive(true);

        account.closeAccount();

        Assertions.assertFalse(account.isActive());
    }
}