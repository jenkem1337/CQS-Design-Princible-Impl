import dummy.example.*;
import org.cqs.CommandDispatcherMediator;
import org.cqs.QueryDispatcherMediator;
import org.cqs.RequestStrategy;
import org.cqs.impl.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RequestStrategyTest {
    private RequestStrategy commandRequestStrategy;
    private RequestStrategy queryRequestStrategy;
    private CommandDispatcherMediator commandDispatcherMediator;
    private QueryDispatcherMediator queryDispatcherMediator;

    private CounterDao counterDao = new CounterInMemoryDb();
    @BeforeEach
    void setUp() {
        commandRequestStrategy = new CommandRequestStrategy();
        queryRequestStrategy = new QueryRequestStrategy();

        commandDispatcherMediator = new CommandDispatcherMediatorImpl((BaseDispatcherMediatorComponent) commandRequestStrategy);
        queryDispatcherMediator = new QueryDispatcherMediatorImpl((BaseDispatcherMediatorComponent) queryRequestStrategy);

        queryDispatcherMediator.addHandler(GetACounterQuery.class.getSimpleName(), new GetSingleCounterQueryHandler((counterDao)));
        commandDispatcherMediator.addHandler(IncrementCounterCommand.class.getSimpleName(), new IncrementCounterCommandHandler(counterDao));
    }

    @AfterEach
    void tearDown() {
        commandRequestStrategy = null;
        queryRequestStrategy = null;
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
}
