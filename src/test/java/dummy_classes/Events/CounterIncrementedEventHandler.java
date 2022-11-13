package dummy_classes.Events;

import dummy_classes.CounterEventStore;
import org.cqs.EventHandler;

public class CounterIncrementedEventHandler implements EventHandler<CounterIncremented> {
    private CounterEventStore eventStore;

    public CounterIncrementedEventHandler(CounterEventStore eventStore) {
        this.eventStore = eventStore;
    }

    @Override
    public Void handle(CounterIncremented obj) throws Exception {
        eventStore.persistEvent("counter-incremented-event"+" "+obj.getCounter().getNumber().toString(), obj);
        return null;
    }
}
