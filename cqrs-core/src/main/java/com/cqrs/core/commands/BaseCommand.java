package com.cqrs.core.commands;

import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;

import com.cqrs.core.messages.Message;

@SuperBuilder
@AllArgsConstructor
public abstract class BaseCommand extends Message {
}
