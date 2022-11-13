import dummy_classes.*;
import dummy_classes.Command.IncrementCounterCommand;
import dummy_classes.Command.IncrementCounterCommandHandler;
import dummy_classes.Events.CounterIncremented;
import dummy_classes.Events.CounterIncrementedEventHandler;
import dummy_classes.Model.Counter;
import dummy_classes.Queries.GetACounterQuery;
import dummy_classes.Queries.GetSingleCounterQueryHandler;
import org.cqs.CommandDispatcherMediator;
import org.cqs.EventDispatcherMediator;
import org.cqs.QueryDispatcherMediator;
import org.cqs.RequestStrategy;
import org.cqs.impl.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class RequestStrategyTest {
    private RequestStrategy commandRequestStrategy;
    private RequestStrategy queryRequestStrategy;
    private RequestStrategy eventRequestStrategy;
    private CommandDispatcherMediator commandDispatcherMediator;
    private QueryDispatcherMediator queryDispatcherMediator;
    private EventDispatcherMediator eventDispatcherMediator;

    private CounterDao counterDao = new CounterInMemoryDb();
    @BeforeEach
    void setUp() {
        var eventPublisherContext = new EventPublisherContextFactory(eventDispatcherMediator -> {
            eventDispatcherMediator.addHandler(CounterIncremented.class.getSimpleName(), Arrays.asList(new CounterIncrementedEventHandler(CounterEventStoreImpl.getInstance())));
        }).createAnInstanceOfEventPublisherContext();

        commandRequestStrategy = new CommandRequestStrategy();
        queryRequestStrategy = new QueryRequestStrategy();
        eventRequestStrategy = new EventRequestStrategy();

        commandDispatcherMediator = new CommandDispatcherMediatorImpl((BaseDispatcherMediatorComponent) commandRequestStrategy);
        queryDispatcherMediator = new QueryDispatcherMediatorImpl((BaseDispatcherMediatorComponent) queryRequestStrategy);
        eventDispatcherMediator = new EventDispatcherMediatorImpl((BaseDispatcherMediatorComponent) eventRequestStrategy);

        eventDispatcherMediator.addHandler(CounterIncremented.class.getSimpleName(), Arrays.asList(new CounterIncrementedEventHandler(CounterEventStoreImpl.getInstance())));
        queryDispatcherMediator.addHandler(GetACounterQuery.class.getSimpleName(), new GetSingleCounterQueryHandler((counterDao)));
        commandDispatcherMediator.addHandler(IncrementCounterCommand.class.getSimpleName(), new IncrementCounterCommandHandler(counterDao, eventPublisherContext));
    }

    @AfterEach
    void tearDown() {
        commandRequestStrategy = null;
        queryRequestStrategy = null;
        eventRequestStrategy = null;
        eventDispatcherMediator = null;
        commandDispatcherMediator = null;
        queryDispatcherMediator = null;
        counterDao = null;
    }

    @Test
    void triggerQueryRequestHandlerThenGetCounterObject() throws Exception {
        Counter counter = (Counter) queryRequestStrategy.dispatch(new GetACounterQuery("counter1"));
        assertEquals(counter.getNumber(), 0);
    }

    @Test
    void triggerCommandRequestHandlerThanIncrementCounterAndGetCounter() throws Exception {
        commandRequestStrategy.dispatch(new IncrementCounterCommand("counter1"));
        Counter counter = (Counter) queryRequestStrategy.dispatch(new GetACounterQuery("counter1"));
        assertEquals(counter.getNumber(), 1);
    }
    @Test
    public void itShouldReturnNullWhenTriggeredEventRequestStrategy() throws Exception {
        var response = eventRequestStrategy.dispatch(new CounterIncremented(new Counter(), LocalDateTime.now()));
        assertNull(response);
    }
}
