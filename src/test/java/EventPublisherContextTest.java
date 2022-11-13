import dummy_classes.Command.DecrementCounterCommand;
import dummy_classes.Command.DecrementCounterCommandHandler;
import dummy_classes.Command.IncrementCounterCommand;
import dummy_classes.Command.IncrementCounterCommandHandler;
import dummy_classes.CounterEventStore;
import dummy_classes.CounterEventStoreImpl;
import dummy_classes.CounterInMemoryDb;
import dummy_classes.Events.*;
import dummy_classes.Model.Counter;
import dummy_classes.SingletonDataHolder;
import org.cqs.impl.DispatcherContext;
import org.cqs.impl.DispatcherContextFactory;
import org.cqs.impl.EventPublisherContext;
import org.cqs.impl.EventPublisherContextFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class EventPublisherContextTest {
    private EventPublisherContext eventPublisherContext;
    private DispatcherContext dispatcherContext;

    @BeforeEach
    void setUp() {
        eventPublisherContext = new EventPublisherContextFactory(eventDispatcherMediator -> {

            eventDispatcherMediator
                    .addHandler(CounterIncremented.class.getSimpleName(), Arrays.asList(new CounterIncrementedEventHandler(CounterEventStoreImpl.getInstance()), new SingletonDataHolderEventHandler()))
                    .addHandler(CounterDecremented.class.getSimpleName(), Arrays.asList(new CounterDecrementedEventHandler(CounterEventStoreImpl.getInstance())));

        }).createAnInstanceOfEventPublisherContext();

        dispatcherContext = new DispatcherContextFactory((commandDispatcherMediator, queryDispatcherMediator) -> {

            commandDispatcherMediator.addHandler(IncrementCounterCommand.class.getSimpleName(), new IncrementCounterCommandHandler(new CounterInMemoryDb(), eventPublisherContext))
                    .addHandler(DecrementCounterCommand.class.getSimpleName(), new DecrementCounterCommandHandler(new CounterInMemoryDb(), eventPublisherContext));

        }).createAnInstanceOfDispatcherContext();
    }

    @AfterEach
    void tearDown() {
        eventPublisherContext = null;
        dispatcherContext = null;
    }
    @Test
    public void itShouldPublishEventWithAggregateRoot() throws Exception {
        var counter = new Counter();
        counter.increment();
        assertNull(eventPublisherContext.publish(counter));
    }
    @Test
    public void itShouldPublishWithEvent() throws Exception {
        assertNull(eventPublisherContext.publish(new CounterDecremented(new Counter(), LocalDateTime.now())));
    }
    @Test
    public void itShouldPublishEventWith_mergeAggregateRootMethod() throws Exception {
        Counter counter = eventPublisherContext.mergeAggregateRoot(new Counter(999));
        counter.decrement();
        counter.increment();
        counter.increment();
        assertEquals(counter.getEventQueue().size(), 3);
        counter.publishEvents();
        assertEquals(counter.getEventQueue().size(), 0);

    }

    @Test
    public void itShouldReturnCorrectSingletonDataWhenTriggeredIncrementCounterCommandHandlerThenSingletonDataHolderEventHandler() throws Exception {
        dispatcherContext.send(new IncrementCounterCommand("counter1"));
        var singletonDataHolder = SingletonDataHolder.getInstance();
        assertEquals(singletonDataHolder.getData(), 1);
    }
    @Test
    public void itShouldReturnCorrectEventStoreValueAndSingletonDataWhenTriggeredIncrementCounterCommandHandler() throws Exception {
        dispatcherContext.send(new IncrementCounterCommand("counter1"));
        var eventStore = CounterEventStoreImpl.getInstance();
        var event = (CounterIncremented) eventStore.getAnEvent("counter-incremented-event 1");
        var singletonDataHolder = SingletonDataHolder.getInstance();

        assertEquals(event.getCounter().getNumber(), 1);
        assertEquals(singletonDataHolder.getData(), 1);

    }
    @Test
    public void itShouldSaveCounterIncrementedEventToEventStoreWhenTriggeredIncrementCounterCommandHandler() throws Exception {
        dispatcherContext.send(new IncrementCounterCommand("counter1"));
        var eventStore = CounterEventStoreImpl.getInstance();
        var counterIncrementedEvent = eventStore.getAnEvent("counter-incremented-event 1");

        assertTrue(counterIncrementedEvent instanceof CounterIncremented);
        assertEquals(((CounterIncremented) counterIncrementedEvent).getCounter().getNumber(), 1);
    }

}
