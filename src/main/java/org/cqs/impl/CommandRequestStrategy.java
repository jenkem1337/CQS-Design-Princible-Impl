package org.cqs.impl;

import org.cqs.Command;
import org.cqs.RequestStrategy;

public class CommandRequestStrategy extends BaseDispatcherMediatorComponent implements RequestStrategy<Command> {
    @Override
    public Object dispatch(Command obj) throws Exception {
        return commandDispatcherMediator.handle(obj);
    }
}
