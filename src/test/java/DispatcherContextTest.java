import dummy.example.*;
import org.cqs.impl.DispatcherContext;

import org.cqs.impl.DispatcherContextFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class DispatcherContextTest {
    private DispatcherContext dispatcherContext;

    @BeforeEach
    void setUp() {
        var dispatcherContextFactory = new DispatcherContextFactory( (commandDispatcherMediator, queryDispatcherMediator) -> {
            var inMemoryDb = new CounterInMemoryDb();
            commandDispatcherMediator
                    .addHandler(CreateCounterCommand.class.getSimpleName(), new CreateCounterCommandHandler(inMemoryDb))
                    .addHandler(IncrementCounterCommand.class.getSimpleName(), new IncrementCounterCommandHandler(inMemoryDb))
                    .addHandler(DecrementCounterCommand.class.getSimpleName(), new DecrementCounterCommandHandler(inMemoryDb));

            queryDispatcherMediator.addHandler(GetACounterQuery.class.getSimpleName(), new GetSingleCounterQueryHandler(inMemoryDb));

        });
        this.dispatcherContext = dispatcherContextFactory.createAnInstanceOfDispatcherContext();

    }

    @AfterEach
    void tearDown() {
        dispatcherContext = null;
    }

    @Test
    void createAnInstanceOfDispatcherContext() {
        var dispatcherContextFactory = new DispatcherContextFactory(((commandDispatcherMediator, queryDispatcherMediator) -> {

            commandDispatcherMediator.addHandler(CreateCounterCommand.class.getSimpleName(), new CreateCounterCommandHandler(new CounterInMemoryDb()));
            queryDispatcherMediator.addHandler(GetACounterQuery.class.getSimpleName(), new GetSingleCounterQueryHandler(new CounterInMemoryDb()));

        }));

        var _dispatcherContext = dispatcherContextFactory.createAnInstanceOfDispatcherContext();
        assertTrue(_dispatcherContext instanceof  DispatcherContext);
    }


    @Test
    void triggeringCreateCounterCommandHandlerAndThenChecked() throws Exception {

        dispatcherContext.send(new CreateCounterCommand("counter2", 1));
        Counter c =  dispatcherContext.send(new GetACounterQuery("counter2"));
        assertEquals(c.getNumber(), 1);
    }

    @Test
    void triggeringIncrementCounterCommandHandlerAndThenChecked() throws Exception {
        dispatcherContext.send(new IncrementCounterCommand("counter1"));
        Counter counter =  dispatcherContext.send(new GetACounterQuery("counter1"));
        assertEquals(counter.getNumber(), 1);
    }

    @Test
    void triggeringDecrementCounterCommandHandlerAndThenChecked() throws Exception {
        dispatcherContext.send(new DecrementCounterCommand("counter1"));
        Counter counter = dispatcherContext.send(new GetACounterQuery("counter1"));
        assertEquals(counter.getNumber(), -1);

    }

    @Test
    void throwExceptionWhenDispatcherContextHashMapKeyDoesntExist(){
        Exception exception = assertThrows(Exception.class, () -> dispatcherContext.send("blablabla"));

        String exceptionMessage = exception.getMessage();

        assertEquals(exceptionMessage, "Your key doesnt exist in hash map");
    }


}
