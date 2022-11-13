import dummy_classes.CounterEventStoreImpl;
import dummy_classes.CounterInMemoryDb;
import dummy_classes.Command.DecrementCounterCommand;
import dummy_classes.Command.IncrementCounterCommand;
import dummy_classes.Command.IncrementCounterCommandHandler;
import dummy_classes.Events.CounterIncremented;
import dummy_classes.Events.CounterIncrementedEventHandler;
import org.cqs.CommandDispatcherMediator;
import org.cqs.impl.BaseDispatcherMediatorComponent;
import org.cqs.impl.CommandDispatcherMediatorImpl;
import org.cqs.impl.EventPublisherContextFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class CommandDispatcherMediatorTest {
    private CommandDispatcherMediator commandDispatcherMediator;

    @BeforeEach
    void setUp() {
        var eventPublisherContext = new EventPublisherContextFactory(eventDispatcherMediator -> {

            eventDispatcherMediator.addHandler(CounterIncremented.class.getSimpleName(), Arrays.asList(new CounterIncrementedEventHandler(CounterEventStoreImpl.getInstance())));

        }).createAnInstanceOfEventPublisherContext();

        commandDispatcherMediator = new CommandDispatcherMediatorImpl(getLocalBaseMediatorComponent());
        commandDispatcherMediator.addHandler(IncrementCounterCommand.class.getSimpleName(), new IncrementCounterCommandHandler(new CounterInMemoryDb(), eventPublisherContext));
    }

    @AfterEach
    void tearDown() {
        commandDispatcherMediator = null;
    }

    @Test
    void verifyHandleMethod() throws Exception {
        assertNull(commandDispatcherMediator.handle(new IncrementCounterCommand("counter1")));
    }
    @Test
    void shouldThrowExceptionWhenNotExistCommand() throws Exception{
        Exception exception = assertThrows(Exception.class, () -> commandDispatcherMediator.handle(new DecrementCounterCommand("counter1")));
        String exceptionMessage = exception.getMessage();

        assertEquals(exceptionMessage, "This command handler doesnt exist");
    }

    private BaseDispatcherMediatorComponent getLocalBaseMediatorComponent(){
        class ConcreteDispatcherMediatorComponent extends BaseDispatcherMediatorComponent {
            @Override
            public void setCommandDispatcherMediator (CommandDispatcherMediator c){

            }
        }
        return new ConcreteDispatcherMediatorComponent();
    }

}
