package dummy_classes;

import org.cqs.Event;

import java.util.HashMap;
import java.util.Map;

public class CounterEventStoreImpl implements CounterEventStore{
    private static CounterEventStoreImpl counterEventStore;
    private Map<String, Event> eventStore;
    private CounterEventStoreImpl(){
        eventStore = new HashMap<>();
    }

    @Override
    public void persistEvent(String eventKey, Event event) {
        eventStore.put(eventKey, event);
    }

    @Override
    public Event getAnEvent(String eventKey) throws Exception {
        if(!eventStore.containsKey(eventKey)){
            throw new Exception("This event doesnt exist");
        }
        return eventStore.get(eventKey);
    }

    public static CounterEventStore getInstance(){
        if(counterEventStore == null){
            counterEventStore = new CounterEventStoreImpl();
        }
        return counterEventStore;
    }
}
