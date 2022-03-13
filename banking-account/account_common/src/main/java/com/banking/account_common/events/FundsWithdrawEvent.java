package com.banking.account_common.events;

import com.banking.cqrs_core.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class FundsWithdrawEvent extends BaseEvent {
    private double amount;
}
