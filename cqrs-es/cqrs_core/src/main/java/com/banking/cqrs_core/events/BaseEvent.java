package com.banking.cqrs_core.events;

import com.banking.cqrs_core.messages.Message;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public abstract class BaseEvent extends Message {
    private int version;

}
