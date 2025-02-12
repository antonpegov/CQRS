package com.cqrs.account.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cqrs.core.infrastructure.CommandDispatcher;

import jakarta.annotation.PostConstruct;

import com.cqrs.account.command.api.commands.CloseAccountCommand;
import com.cqrs.account.command.api.commands.CommandHandler;
import com.cqrs.account.command.api.commands.DepositFundsCommand;
import com.cqrs.account.command.api.commands.OpenAccountCommand;
import com.cqrs.account.command.api.commands.WithdrawFundsCommand;

@SpringBootApplication
public class AccountCommandApplication {

    @Autowired
    private CommandDispatcher commandDispatcher;

    @Autowired
    private CommandHandler commandHandler;

    public static void main(String[] args) {
        SpringApplication.run(AccountCommandApplication.class, args);
    }

    @PostConstruct
    public void registerHandlers() {
        commandDispatcher.registerHandler(OpenAccountCommand.class, commandHandler::handle);
        commandDispatcher.registerHandler(DepositFundsCommand.class, commandHandler::handle);
        commandDispatcher.registerHandler(WithdrawFundsCommand.class, commandHandler::handle);
        commandDispatcher.registerHandler(CloseAccountCommand.class, commandHandler::handle);
    }

}
