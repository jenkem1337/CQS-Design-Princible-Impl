package org.cqs.impl;

import org.cqs.Command;
import org.cqs.CommandDispatcherMediator;
import org.cqs.CommandHandler;

import java.util.HashMap;
import java.util.Map;

public class CommandDispatcherMediatorImpl implements CommandDispatcherMediator {
    private Map<String, CommandHandler> commandHandlerHashMap = new HashMap<>();

    public CommandDispatcherMediatorImpl(BaseDispatcherMediatorComponent component){
        component.setCommandDispatcherMediator(this);
    }

    @Override
    public Object handle(Command obj) throws Exception {
        if(!commandHandlerHashMap.containsKey(obj.getClass().getSimpleName())){
            throw new Exception("This command handler doesnt exist");
        }
        CommandHandler<Command> _handler = commandHandlerHashMap.get(obj.getClass().getSimpleName());
        return _handler.handle(obj);
    }

    @Override
    public CommandDispatcherMediatorImpl addHandler(String key, CommandHandler handler) {
        commandHandlerHashMap.put(key, handler);
        return this;
    }
}
