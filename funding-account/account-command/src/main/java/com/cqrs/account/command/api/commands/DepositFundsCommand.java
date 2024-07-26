package com.cqrs.account.command.api.commands;

import com.cqrs.core.commands.BaseCommand;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DepositFundsCommand extends BaseCommand {
    private double amount;
}
