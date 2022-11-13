package org.cqs.impl;

import org.cqs.CommandDispatcherMediator;
import org.cqs.EventDispatcherMediator;
import org.cqs.QueryDispatcherMediator;

public abstract class BaseDispatcherMediatorComponent {
    protected CommandDispatcherMediator commandDispatcherMediator;
    protected QueryDispatcherMediator queryDispatcherMediator;
    protected EventDispatcherMediator eventDispatcherMediator;

    public void setCommandDispatcherMediator(CommandDispatcherMediator commandDispatcherMediator){
        this.commandDispatcherMediator = commandDispatcherMediator;
    }

    public void setQueryDispatcherMediator(QueryDispatcherMediator queryDispatcherMediator) {
        this.queryDispatcherMediator = queryDispatcherMediator;
    }

    public void setEventDispatcherMediator(EventDispatcherMediator eventDispatcherMediator) {
        this.eventDispatcherMediator = eventDispatcherMediator;
    }
}
