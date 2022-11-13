package org.cqs.impl;
import org.cqs.Event;
import org.cqs.RequestStrategy;
import org.w3c.dom.css.Counter;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventPublisherContext {
    private RequestStrategy<Event> eventRequestStrategy;
    public EventPublisherContext(RequestStrategy<Event> eventRequestStrategy) {
        this.eventRequestStrategy = eventRequestStrategy;
    }

    public Void publish(AggregateRoot aggregateRoot) throws Exception {
        var queueSize = aggregateRoot.getEventQueue().size();
        for(int i = 0; i < queueSize ; i++){
            var event = aggregateRoot.dequeueEvent();
            eventRequestStrategy.dispatch(event);
        }
        return null;
    }
    public Void publish(Event event) throws Exception {
        eventRequestStrategy.dispatch(event);
        return null;
    }

    public <R> R mergeAggregateRoot(AggregateRoot aggregateRoot) {
        aggregateRoot.setEventPublisherContext(this);
        return (R) aggregateRoot;
    }
}
