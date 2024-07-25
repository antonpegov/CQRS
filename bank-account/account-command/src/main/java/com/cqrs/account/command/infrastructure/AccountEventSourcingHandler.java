package com.cqrs.account.command.infrastructure;

import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cqrs.account.command.domain.AccountAggregate;

import com.cqrs.core.domain.AggregateRoot;
import com.cqrs.core.handlers.EventSourcingHandler;
import com.cqrs.core.infrastructure.EventStore;

@Service
public class AccountEventSourcingHandler implements EventSourcingHandler<AccountAggregate> {
    @Autowired
    private EventStore eventStore;

    @Override
    public void save(AggregateRoot aggregate) {
        eventStore.saveEvents(aggregate.getId(), aggregate.getUncommitedChanges(), aggregate.getVersion());
        aggregate.markChangesAsCommited();
    }

    @Override
    public AccountAggregate getById(String id) {
        int latestVersion;
        var aggregate = new AccountAggregate();
        var events = eventStore.getEventsForAggregate(id);

        if (events != null && !events.isEmpty()) {
            aggregate.replayEvents(events);
            latestVersion = events.stream().map(e -> e.getVersion()).max(Comparator.naturalOrder()).get();
            aggregate.setVersion(latestVersion);
        }

        return aggregate;
    }

}
