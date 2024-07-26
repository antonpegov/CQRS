package com.cqrs.core.events;

import com.cqrs.core.messages.Message;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public abstract class BaseEvent extends Message {
    private int version;
}
