package org.cqs.impl;

import org.cqs.Event;
import org.cqs.RequestStrategy;

public class EventRequestStrategy extends BaseDispatcherMediatorComponent implements RequestStrategy<Event>{
    @Override
    public Object dispatch(Event obj) throws Exception {
        return eventDispatcherMediator.handle(obj);
    }
}
