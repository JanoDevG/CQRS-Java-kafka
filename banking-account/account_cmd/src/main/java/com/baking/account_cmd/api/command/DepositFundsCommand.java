package com.baking.account_cmd.api.command;

import com.banking.cqrs_core.commands.BaseCommand;
import lombok.Data;

@Data
public class DepositFundsCommand extends BaseCommand {
    private double amount;
}
