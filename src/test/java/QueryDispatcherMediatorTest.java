import dummy.example.Counter;
import dummy.example.CounterInMemoryDb;
import dummy.example.GetACounterQuery;
import dummy.example.GetSingleCounterQueryHandler;
import org.cqs.CommandDispatcherMediator;
import org.cqs.Query;
import org.cqs.QueryDispatcherMediator;
import org.cqs.impl.BaseDispatcherMediatorComponent;
import org.cqs.impl.QueryDispatcherMediatorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class QueryDispatcherMediatorTest {
    private QueryDispatcherMediator queryDispatcherMediator;

    @BeforeEach
    void setUp() {
        queryDispatcherMediator = new QueryDispatcherMediatorImpl(getLocalBaseMediatorComponent());
        queryDispatcherMediator.addHandler(GetACounterQuery.class.getSimpleName(), new GetSingleCounterQueryHandler(new CounterInMemoryDb()));
    }

    @Test
    void itShouldReturnCounterNumberWhenTriggeredHandleMethod() throws Exception {
        Counter counter = (Counter) queryDispatcherMediator.handle(new GetACounterQuery("counter1"));
        assertEquals(counter.getNumber(), 0);
    }

    @Test
    void itShouldThrowExceptionWhenGivenDoesntExistMapKey(){
        Exception exception = assertThrows(Exception.class, () -> queryDispatcherMediator.handle(getLocalDummyQuery()));
        assertEquals(exception.getMessage(), "This query handler doesnt exist");
    }
    private BaseDispatcherMediatorComponent getLocalBaseMediatorComponent(){
        class ConcreteDispatcherMediatorComponent extends BaseDispatcherMediatorComponent {
            public void setQueryDispatcherMediator(CommandDispatcherMediator c){}
        }
        return new ConcreteDispatcherMediatorComponent();
    }
    private Query getLocalDummyQuery(){
        class DummyQuery implements Query{}
        return new DummyQuery();
    }
}

