package com.baking.account_cmd.infrastructure;

import com.banking.cqrs_core.commands.BaseCommand;
import com.banking.cqrs_core.commands.CommandHandlerMethod;
import com.banking.cqrs_core.infraestructure.CommandDispatcher;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class AccountCommandDispatcher implements CommandDispatcher {

    private final Map<Class<? extends BaseCommand>, List<CommandHandlerMethod>> routes = new HashMap<>();

    @Override
    public <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handlerMethod) {
        var handlers = routes.computeIfAbsent(type, c -> new LinkedList<>());
        handlers.add(handlerMethod);
    }

    @Override
    public void send(BaseCommand command) {
        var handlres = routes.get(command.getClass());
        if (handlres == null || handlres.size() == 0) {
            throw new RuntimeException("El Command Handler no fue registrado");
        }
        if (handlres.size() > 1) {
            throw new RuntimeException("No pude enviar un command que tenga mas de un handler");
        }
        handlres.get(0).handle(command);
    }
}
