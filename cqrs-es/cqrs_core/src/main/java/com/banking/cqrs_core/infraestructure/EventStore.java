package com.banking.cqrs_core.infraestructure;

import com.banking.cqrs_core.events.BaseEvent;

import java.util.List;

public interface EventStore {

    void saveEvents(String aggregateId, Iterable<BaseEvent> events, int expectedVersion);

    List<BaseEvent> getEvents(String aggregateId);
}
