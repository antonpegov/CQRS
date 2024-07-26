package com.cqrs.account.common.events;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import com.cqrs.core.events.BaseEvent;

@Data
@SuperBuilder
public class FundsDepositedEvent extends BaseEvent {
    private double amount;
}
