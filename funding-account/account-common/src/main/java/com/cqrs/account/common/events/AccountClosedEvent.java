package com.cqrs.account.common.events;

import com.cqrs.core.events.BaseEvent;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class AccountClosedEvent extends BaseEvent {
}
