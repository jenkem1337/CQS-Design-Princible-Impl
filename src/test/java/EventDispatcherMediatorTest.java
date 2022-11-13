import dummy_classes.CounterEventStore;
import dummy_classes.CounterEventStoreImpl;
import dummy_classes.Events.CounterDecremented;
import dummy_classes.Events.CounterDecrementedEventHandler;
import dummy_classes.Events.CounterIncremented;
import dummy_classes.Events.CounterIncrementedEventHandler;
import dummy_classes.Model.Counter;
import org.cqs.Event;
import org.cqs.EventDispatcherMediator;
import org.cqs.impl.BaseDispatcherMediatorComponent;
import org.cqs.impl.EventDispatcherMediatorImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EventDispatcherMediatorTest {
    private EventDispatcherMediator eventDispatcherMediator;
    @BeforeEach
    void setUp() {
        eventDispatcherMediator = new EventDispatcherMediatorImpl(getLocalBaseDispatcherMediatorComponent());
        eventDispatcherMediator.addHandler(CounterIncremented.class.getSimpleName(), Arrays.asList(new CounterIncrementedEventHandler(CounterEventStoreImpl.getInstance())))
                .addHandler(CounterDecremented.class.getSimpleName(), Arrays.asList(new CounterDecrementedEventHandler(CounterEventStoreImpl.getInstance())));
    }

    @AfterEach
    void tearDown() {
        eventDispatcherMediator = null;
    }
    @Test
    public void itShouldReturnNullWhenTriggeredCounterIncrementedEvent() throws Exception {
        assertNull(eventDispatcherMediator.handle(new CounterDecremented(new Counter(), LocalDateTime.now())));
    }

    @Test
    public void itShouldReturnNullWhenTriggeredCounterDecrementedEvent() throws Exception {
        assertNull(eventDispatcherMediator.handle(new CounterDecremented(new Counter(), LocalDateTime.now())));
    }
    @Test
    public void itShouldThrowExceptionWhenGivenDoesntExistKey(){
        Exception exception = assertThrows(Exception.class, () -> eventDispatcherMediator.handle(getDummyEvent()));
        assertEquals(exception.getMessage(), "This event handler doesnt exist");
    }
    @Test
    public void itShouldReturnEventInstanceFromEventStoreWhenGivenValidKey() throws Exception {
        eventDispatcherMediator.handle(new CounterDecremented(new Counter(), LocalDateTime.now()));
        var eventStore = CounterEventStoreImpl.getInstance();
        var counterDecrementedEvent = eventStore.getAnEvent("counter-decremented-event"+" "+"0");
        assertTrue(counterDecrementedEvent instanceof CounterDecremented );
    }
    private BaseDispatcherMediatorComponent getLocalBaseDispatcherMediatorComponent(){
        class ConcreteDispatcherMediatorComponent extends BaseDispatcherMediatorComponent {
            @Override
            public void setEventDispatcherMediator(EventDispatcherMediator c){}
        }
        return new ConcreteDispatcherMediatorComponent();

    }
    private Event getDummyEvent(){
        class AlperTungaOldiMi implements Event {
            @Override
            public LocalDateTime getLocalDateTime() {
                return null;
            }
        }
        return new AlperTungaOldiMi();
    }
}
