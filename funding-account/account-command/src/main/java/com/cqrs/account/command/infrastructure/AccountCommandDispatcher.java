package com.cqrs.account.command.infrastructure;

import org.springframework.stereotype.Service;

import com.cqrs.core.commands.BaseCommand;
import com.cqrs.core.commands.CommandHandlerMethod;
import com.cqrs.core.infrastructure.CommandDispatcher;

import java.util.LinkedList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AccountCommandDispatcher implements CommandDispatcher {
    private final Map<Class<? extends BaseCommand>, List<CommandHandlerMethod>> routes = new HashMap<>();

    @Override
    public <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler) {
        var handlers = routes.computeIfAbsent(type, c -> new LinkedList<>());
        handlers.add(handler);
    }

    @Override
    public void send(BaseCommand command) {
        var handlers = routes.get(command.getClass());

        if (handlers == null || handlers.isEmpty()) {
            throw new IllegalArgumentException("No handler registered");
        } else if (handlers.size() != 1) {
            throw new IllegalArgumentException("Too many handlers registered");
        }

        handlers.get(0).handle(command);

    }

}
