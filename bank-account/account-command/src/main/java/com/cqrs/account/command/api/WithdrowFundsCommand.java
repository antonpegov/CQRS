package com.cqrs.account.command.api;

import com.cqrs.commands.BaseCommand;

import lombok.Data;

@Data
public class WithdrowFundsCommand extends BaseCommand {
    private double amount;
}
