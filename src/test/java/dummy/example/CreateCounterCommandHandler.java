package dummy.example;

import org.cqs.CommandHandler;

public class CreateCounterCommandHandler implements CommandHandler<CreateCounterCommand> {
    private CounterDao counterDao;

    public CreateCounterCommandHandler(CounterDao counterDao){
        this.counterDao = counterDao;
    }
    @Override
    public Void handle(CreateCounterCommand obj) {
        var counter = new Counter(obj.getNumber());
        counterDao.persistCounter(obj.get_hashKey(), counter);
        return null;
    }
}
