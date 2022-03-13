package com.baking.account_cmd.api.command;

import com.banking.account_common.dto.AccountType;
import com.banking.cqrs_core.commands.BaseCommand;
import lombok.Data;

@Data
public class OpenAccountCommand extends BaseCommand {
    private String accountHolder;
    private AccountType accountType;
    private double openingBalance;
}
