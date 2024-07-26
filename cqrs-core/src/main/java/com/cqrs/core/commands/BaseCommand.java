package com.cqrs.core.commands;

import lombok.AllArgsConstructor;

import com.cqrs.core.messages.Message;

@AllArgsConstructor
public abstract class BaseCommand extends Message {
}
