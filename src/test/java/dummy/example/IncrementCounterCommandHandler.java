package dummy.example;

import org.cqs.CommandHandler;

public class IncrementCounterCommandHandler implements CommandHandler<IncrementCounterCommand> {
    private CounterDao counterDao;

    public IncrementCounterCommandHandler(CounterDao counterDao) {
        this.counterDao = counterDao;
    }

    @Override
    public Void handle(IncrementCounterCommand obj) throws Exception {
        Counter counter = counterDao.getCounter(obj.getHashKey());
        counter.increment();
        counterDao.persistCounter(obj.getHashKey(), counter);
        return null;
    }
}
