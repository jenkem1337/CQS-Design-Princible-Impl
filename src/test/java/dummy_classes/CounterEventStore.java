package dummy_classes;

import org.cqs.Event;

public interface CounterEventStore {
    void persistEvent(String eventKey, Event event);
    Event getAnEvent(String eventKey) throws Exception;
}
