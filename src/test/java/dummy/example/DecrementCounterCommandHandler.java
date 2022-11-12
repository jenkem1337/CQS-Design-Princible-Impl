package dummy.example;

import org.cqs.CommandHandler;

public class DecrementCounterCommandHandler implements CommandHandler<DecrementCounterCommand> {
    private CounterDao counterDao;

    public DecrementCounterCommandHandler(CounterDao counterDao) {
        this.counterDao = counterDao;
    }

    @Override
    public Void handle(DecrementCounterCommand obj) throws Exception {
        Counter counter = counterDao.getCounter(obj.getHashKey());
        counter.decrement();
        counterDao.persistCounter(obj.getHashKey(), counter);

        return null;
    }
}
