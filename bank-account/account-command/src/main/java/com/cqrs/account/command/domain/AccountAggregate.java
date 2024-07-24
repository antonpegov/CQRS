package com.cqrs.account.command.domain;

import lombok.Data;

import java.util.Date;

import com.cqrs.account.command.api.OpenAccountCommand;
import com.cqrs.account.common.events.AccountClosedEvent;
import com.cqrs.account.common.events.AccountOpenedEvent;
import com.cqrs.account.common.events.FundsDepositedEvent;
import com.cqrs.account.common.events.FundsWithdrownEvent;
import com.cqrs.core.domain.AggregateRoot;

@Data
public class AccountAggregate extends AggregateRoot {
    private boolean active;
    private double balance;

    public AccountAggregate(OpenAccountCommand command) {
        riseEvent(AccountOpenedEvent.builder()
                .id(command.getId())
                .accountHolder(command.getAccountHolder())
                .openingBalance(command.getInitialBalance())
                .accountType(command.getAccountType())
                .createdDate(new Date())
                .build());
    }

    // #region Api

    public void apply(AccountOpenedEvent event) {
        this.id = event.getId();
        this.active = true;
        this.balance = event.getOpeningBalance();
    }

    public void apply(FundsDepositedEvent event) {
        this.balance += event.getAmount();
    }

    public void apply(AccountClosedEvent event) {
        this.active = false;
    }

    public void apply(FundsWithdrownEvent event) {
        this.balance -= event.getAmount();
    }

    // #endregion
    // #region Buisness Logic

    public void depositFunds(double amount) {
        if (!active) {
            throw new IllegalStateException("Account is not active");
        }

        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be greater than 0");
        }

        riseEvent(FundsDepositedEvent.builder()
                .id(this.id)
                .amount(amount)
                .build());
    }

    public void withdrawFunds(double amount) {
        if (!active) {
            throw new IllegalStateException("Account is not active");
        }

        if (amount <= 0) {
            throw new IllegalArgumentException("Withdraw amount must be greater than 0");
        }

        if (balance < amount) {
            throw new IllegalStateException("Insufficient funds");
        }

        riseEvent(FundsWithdrownEvent.builder()
                .id(this.id)
                .amount(amount)
                .build());
    }

    public void closeAccount() {
        if (!active) {
            throw new IllegalStateException("Account is not active");
        }

        riseEvent(AccountClosedEvent.builder()
                .id(this.id)
                .build());
    }

    // #endregion
}
