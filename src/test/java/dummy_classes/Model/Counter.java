package dummy_classes.Model;

import dummy_classes.Events.CounterDecremented;
import dummy_classes.Events.CounterIncremented;
import org.cqs.impl.AggregateRoot;

import java.time.LocalDateTime;

public class Counter extends AggregateRoot<Long> {
    private Integer _number;

    public Counter() {
        super(1L);
        _number = 0;
    }

    public Counter(Integer integer){
        super(1L);
        _number = integer;
    }

    public void increment(){
        _number++;
        enqueueEvent(new CounterIncremented(this, LocalDateTime.now()));
    }


    public void decrement() {
        _number--;
        enqueueEvent(new CounterDecremented(this, LocalDateTime.now()));
    }

    public Integer getNumber(){
        return _number;
    }
}