package com.baking.account_cmd.infrastructure;

import com.baking.account_cmd.domain.AccountAggregate;
import com.baking.account_cmd.domain.EventStoreRepository;
import com.banking.cqrs_core.events.BaseEvent;
import com.banking.cqrs_core.events.EventModel;
import com.banking.cqrs_core.exceptions.AggregateNotFoundException;
import com.banking.cqrs_core.exceptions.ConcurrencyException;
import com.banking.cqrs_core.infraestructure.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountEventStore implements EventStore {

    @Autowired
    private EventStoreRepository eventStoreRepository;

    @Override
    public void saveEvents(String aggregateId, Iterable<BaseEvent> events, int expectedVersion) {
        var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
        if (expectedVersion != -1 && eventStream.get(eventStream.size() - 1).getVersion() != expectedVersion) {
            throw new ConcurrencyException();

        }
        var version = expectedVersion;
        for (var event : events) {
            version++;
            event.setVersion(version);
            var eventModel = EventModel.builder()
                    .timeStamp(new Date())
                    .aggregateIdentifier(aggregateId)
                    .aggregateType(AccountAggregate.class.getTypeName())
                    .version(version)
                    .eventType(event.getClass().getTypeName())
                    .eventData(event)
                    .build();
            var persistedEvent = eventStoreRepository.save(eventModel);
            if (persistedEvent != null) {
                // producrir un evento para kafka
            }
        }
    }

    @Override
    public List<BaseEvent> getEvents(String aggregateId) {
        var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
        if (eventStream == null || eventStream.isEmpty()) {
            throw new AggregateNotFoundException("La cuenta del banco es incorrecta");
        }
        return eventStream.stream().map(EventModel::getEventData).collect(Collectors.toList());
    }
}
