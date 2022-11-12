import dummy.example.CounterInMemoryDb;
import dummy.example.DecrementCounterCommand;
import dummy.example.IncrementCounterCommand;
import dummy.example.IncrementCounterCommandHandler;
import org.cqs.CommandDispatcherMediator;
import org.cqs.impl.BaseDispatcherMediatorComponent;
import org.cqs.impl.CommandDispatcherMediatorImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CommandDispatcherMediatorTest {
    private CommandDispatcherMediator commandDispatcherMediator;

    @BeforeEach
    void setUp() {
        commandDispatcherMediator = new CommandDispatcherMediatorImpl(getLocalBaseMediatorComponent());
        commandDispatcherMediator.addHandler(IncrementCounterCommand.class.getSimpleName(), new IncrementCounterCommandHandler(new CounterInMemoryDb()));
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
           public void setCommandDispatcherMediator(CommandDispatcherMediator c){}
        }
        return new ConcreteDispatcherMediatorComponent();
    }

}
