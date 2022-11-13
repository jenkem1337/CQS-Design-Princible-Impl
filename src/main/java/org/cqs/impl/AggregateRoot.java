package org.cqs.impl;

import org.cqs.Event;

import java.util.LinkedList;
import java.util.Queue;

public abstract class AggregateRoot<ID> {
    private ID id;
    private Queue<Event> eventQueue;
    private EventPublisherContext eventPublisherContext;


    public AggregateRoot(ID id) {
        this.id = id;
        this.eventQueue = new LinkedList<Event>();
    }
    public void setEventPublisherContext(EventPublisherContext eventPublisherContext) {
        this.eventPublisherContext = eventPublisherContext;
    }

    public void enqueueEvent(Event event) {
        eventQueue.add(event);
    }
    public Event dequeueEvent(){
        return eventQueue.remove();
    }
    public void removeEvents(){
        eventQueue = new LinkedList<>();
    }
    public void publishEvents() throws Exception {
        eventPublisherContext.publish(this);
    }
    public ID getId() {
        return id;
    }

    public Queue<Event> getEventQueue() {
        return eventQueue;
    }
}
