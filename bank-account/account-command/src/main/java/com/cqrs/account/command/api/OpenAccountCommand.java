package com.cqrs.account.command.api;

import com.cqrs.account.common.dto.AccountType;
import com.cqrs.core.commands.BaseCommand;

import lombok.Data;

@Data
public class OpenAccountCommand extends BaseCommand {
    private String accountHolder;
    private double initialBalance;
    private AccountType accountType;
}
