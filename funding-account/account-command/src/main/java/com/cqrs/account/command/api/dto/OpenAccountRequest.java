package com.cqrs.account.command.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import com.cqrs.account.command.api.commands.OpenAccountCommand;
import com.cqrs.account.common.dto.AccountType;

@Data
public class OpenAccountRequest {
    @NotBlank(message = "Account holder field is required")
    private String accountHolder;

    @NotNull(message = "Initial balance field is required")
    private double initialBalance;

    @NotBlank(message = "Account type field is required")
    private String accountType;

    public OpenAccountCommand toCommand(String id) {
        return OpenAccountCommand.builder()
                .accountHolder(accountHolder)
                .initialBalance(initialBalance)
                .accountType(AccountType.valueOf(accountType))
                .id(id)
                .build();
    }
}
