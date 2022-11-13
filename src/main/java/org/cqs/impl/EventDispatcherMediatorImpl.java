package org.cqs.impl;

import org.cqs.DispatcherMediator;
import org.cqs.Event;
import org.cqs.EventDispatcherMediator;
import org.cqs.EventHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EventDispatcherMediatorImpl implements EventDispatcherMediator {
    private Map<String, List<EventHandler>> eventHandlerHashMap;
    public EventDispatcherMediatorImpl(BaseDispatcherMediatorComponent component) {
        component.setEventDispatcherMediator(this);
        eventHandlerHashMap = new HashMap<>();
    }


    @Override
    public Object handle(Event obj) throws Exception {
        if(!eventHandlerHashMap.containsKey(obj.getClass().getSimpleName())) {
            throw new Exception("This event handler doesnt exist");
        }
        List<EventHandler> eventHandlers = eventHandlerHashMap.get(obj.getClass().getSimpleName());
        for(EventHandler eventHandler : eventHandlers) {
            eventHandler.handle(obj);
        }
        return null;
    }

    @Override
    public DispatcherMediator<Event, ?> addHandler(String key, List<EventHandler> handler) {
        eventHandlerHashMap.put(key, handler);
        return this;
    }
}
