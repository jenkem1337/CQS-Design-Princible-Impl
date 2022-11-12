package org.cqs.impl;

import org.cqs.CommandDispatcherMediator;
import org.cqs.QueryDispatcherMediator;

public abstract class BaseDispatcherMediatorComponent {
    protected CommandDispatcherMediator commandDispatcherMediator;
    protected QueryDispatcherMediator queryDispatcherMediator;

    void setCommandDispatcherMediator(CommandDispatcherMediator commandDispatcherMediator){
        this.commandDispatcherMediator = commandDispatcherMediator;
    }

    void setQueryDispatcherMediator(QueryDispatcherMediator queryDispatcherMediator) {
        this.queryDispatcherMediator = queryDispatcherMediator;
    }
}
