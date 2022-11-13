package dummy_classes.Command;

import dummy_classes.CounterDao;
import dummy_classes.Model.Counter;
import org.cqs.CommandHandler;
import org.cqs.impl.EventPublisherContext;

public class IncrementCounterCommandHandler implements CommandHandler<IncrementCounterCommand> {
    private CounterDao counterDao;
    private EventPublisherContext eventPublisherContext;

    public IncrementCounterCommandHandler(CounterDao counterDao, EventPublisherContext eventPublisherContext) {
        this.counterDao = counterDao;
        this.eventPublisherContext = eventPublisherContext;
    }

    @Override
    public Void handle(IncrementCounterCommand obj) throws Exception {
        Counter counter = eventPublisherContext.mergeAggregateRoot(counterDao.getCounter(obj.getHashKey()));
        counter.increment();
        counterDao.persistCounter(obj.getHashKey(), counter);
        counter.publishEvents();
        return null;
    }
}
