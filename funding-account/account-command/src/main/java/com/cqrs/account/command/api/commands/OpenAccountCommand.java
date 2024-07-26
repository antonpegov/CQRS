package com.cqrs.account.command.api.commands;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import com.cqrs.account.common.dto.AccountType;
import com.cqrs.core.commands.BaseCommand;

@Data
@SuperBuilder
public class OpenAccountCommand extends BaseCommand {
    private String accountHolder;
    private double initialBalance;
    private AccountType accountType;
}
