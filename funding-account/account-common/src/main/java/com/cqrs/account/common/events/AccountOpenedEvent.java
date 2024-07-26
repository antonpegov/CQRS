package com.cqrs.account.common.events;

import java.util.Date;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import com.cqrs.account.common.dto.AccountType;
import com.cqrs.core.events.BaseEvent;

@Data
@SuperBuilder
public class AccountOpenedEvent extends BaseEvent {
    private String accountHolder;
    private AccountType accountType;
    private Date createdDate;
    private double openingBalance;
}
