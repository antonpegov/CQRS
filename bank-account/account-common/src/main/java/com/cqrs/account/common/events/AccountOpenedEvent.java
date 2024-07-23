package com.cqrs.account.common.events;

import java.util.Date;

import com.cqrs.account.common.dto.AccountType;
import com.cqrs.core.events.BaseEvent;

import lombok.Data;

@Data
public class AccountOpenedEvent extends BaseEvent {
    private String accountHolder;
    private AccountType accountType;
    private Date createdDate;
    private double openingBalance;
}
