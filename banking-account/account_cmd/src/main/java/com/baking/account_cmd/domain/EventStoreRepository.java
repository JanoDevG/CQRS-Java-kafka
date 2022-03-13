package com.baking.account_cmd.domain;

import com.banking.cqrs_core.events.EventModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EventStoreRepository extends MongoRepository<EventModel, String> {

    List<EventModel> findByAggregateIdentifier(String aggregateIdentifier);
}
