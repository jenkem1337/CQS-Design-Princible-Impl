package dummy_classes.Events;

import dummy_classes.CounterEventStore;
import org.cqs.EventHandler;

public class CounterDecrementedEventHandler implements EventHandler<CounterDecremented> {
    private CounterEventStore eventStore;

    public CounterDecrementedEventHandler(CounterEventStore eventStore) {
        this.eventStore = eventStore;
    }

    @Override
    public Void handle(CounterDecremented obj) throws Exception {
        eventStore.persistEvent("counter-decremented-event"+" "+obj.getCounter().getNumber().toString(), obj);
        return null;
    }
}
