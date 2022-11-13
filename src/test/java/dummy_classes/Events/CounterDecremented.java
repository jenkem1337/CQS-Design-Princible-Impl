package dummy_classes.Events;

import dummy_classes.Model.Counter;
import org.cqs.Event;

import java.time.LocalDateTime;

public class CounterDecremented implements Event {
    private Counter counter;
    private LocalDateTime localDateTime;

    public Counter getCounter() {
        return counter;
    }

    public CounterDecremented(Counter counter, LocalDateTime localDateTime) {
        this.counter = counter;
        this.localDateTime = localDateTime;
    }

    @Override
    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
}
