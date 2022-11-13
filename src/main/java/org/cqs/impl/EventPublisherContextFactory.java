package org.cqs.impl;

import org.cqs.Event;
import org.cqs.EventDispatcherMediator;
import org.cqs.EventDispatcherMediatorCallback;
import org.cqs.RequestStrategy;

public class EventPublisherContextFactory {
    private EventDispatcherMediator eventDispatcherMediator;
    private RequestStrategy<Event> eventRequestStrategy;

    public EventPublisherContextFactory(EventDispatcherMediatorCallback eventDispatcherMediatorCallback) {
        eventRequestStrategy = new EventRequestStrategy();
        eventDispatcherMediator = new EventDispatcherMediatorImpl((BaseDispatcherMediatorComponent) eventRequestStrategy);
        eventDispatcherMediatorCallback.apply(eventDispatcherMediator);
    }

    public EventPublisherContext createAnInstanceOfEventPublisherContext(){
        return new EventPublisherContext(eventRequestStrategy);
    }
}
