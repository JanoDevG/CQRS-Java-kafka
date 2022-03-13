package com.banking.cqrs_core.infraestructure;

import com.banking.cqrs_core.commands.BaseCommand;
import com.banking.cqrs_core.commands.CommandHandlerMethod;

public interface CommandDispatcher {
    <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handlerMethod);
    void send(BaseCommand command);
}
