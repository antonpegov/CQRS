package com.cqrs.core.infrastructure;

import java.util.List;

import com.cqrs.core.events.BaseEvent;

public interface EventStore {
    void saveEvents(String aggregateId, Iterable<BaseEvent> events, int expectedVersion);

    List<BaseEvent> getEventsForAggregate(String aggregateId);
}
