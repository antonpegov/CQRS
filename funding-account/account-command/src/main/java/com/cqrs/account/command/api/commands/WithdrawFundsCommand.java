package com.cqrs.account.command.api.commands;

import com.cqrs.core.commands.BaseCommand;

import lombok.Data;

@Data
public class WithdrawFundsCommand extends BaseCommand {
    private double amount;
}
