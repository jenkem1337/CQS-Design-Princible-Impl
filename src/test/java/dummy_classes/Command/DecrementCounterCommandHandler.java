package dummy_classes.Command;

import dummy_classes.CounterDao;
import dummy_classes.Model.Counter;
import org.cqs.CommandHandler;
import org.cqs.impl.EventPublisherContext;

public class DecrementCounterCommandHandler implements CommandHandler<DecrementCounterCommand> {
    private CounterDao counterDao;
    private EventPublisherContext eventPublisherContext;

    public DecrementCounterCommandHandler(CounterDao counterDao, EventPublisherContext eventPublisherContext) {
        this.counterDao = counterDao;
        this.eventPublisherContext = eventPublisherContext;
    }

    @Override
    public Void handle(DecrementCounterCommand obj) throws Exception {
        Counter counter = eventPublisherContext.mergeAggregateRoot(counterDao.getCounter(obj.getHashKey()));
        counter.decrement();
        counterDao.persistCounter(obj.getHashKey(), counter);
        counter.publishEvents();
        return null;
    }
}
