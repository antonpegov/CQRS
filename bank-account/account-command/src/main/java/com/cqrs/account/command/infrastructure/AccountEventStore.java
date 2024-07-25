package com.cqrs.account.command.infrastructure;

import java.util.Date;
import java.util.List;

import org.apache.kafka.shaded.io.opentelemetry.proto.trace.v1.Span.Event;
import org.springframework.beans.factory.annotation.Autowired;

import com.cqrs.account.command.domain.AccountAggregate;
import com.cqrs.account.command.domain.EventStoreRepository;

import com.cqrs.core.events.BaseEvent;
import com.cqrs.core.events.EventModel;
import com.cqrs.core.exceptions.AggregateNotFoundException;
import com.cqrs.core.exceptions.ConcurrencyException;
import com.cqrs.core.infrastructure.EventStore;

public class AccountEventStore implements EventStore {
    @Autowired
    private EventStoreRepository eventStoreRepository;

    @Override
    public void saveEvents(String aggregateId, Iterable<BaseEvent> events, int expectedVersion) {
        var persistedEvents = eventStoreRepository.findByAggregateId(aggregateId);

        if (expectedVersion != -1 && expectedVersion != persistedEvents.get(persistedEvents.size() - 1).getVersion()) {
            throw new ConcurrencyException("Concurrency issue: the version of the aggregate is not correct");
        }

        var version = expectedVersion;

        for (var event : events) {
            version++;
            event.setVersion(version);

            var eventModel = EventModel.builder()
                    .timeStamp(new Date())
                    .aggregateIdentifier(aggregateId)
                    .aggregateType(AccountAggregate.class.getName())
                    .version(version)
                    .eventType(event.getClass().getName())
                    .event(event)
                    .build();

            var persistedEvent = eventStoreRepository.save(eventModel);

            if (persistedEvent != null) {
                // TODO: Check the event
                // TODO: Publish event to Kafka
            }

        }

    }

    @Override
    public List<BaseEvent> getEventsForAggregate(String aggregateId) {
        var events = eventStoreRepository.findByAggregateId(aggregateId);

        if (events == null || events.isEmpty()) {
            throw new AggregateNotFoundException("Incorect account id provided!");
        }

        return events.stream().map(e -> e.getEvent()).toList();
    }

}
