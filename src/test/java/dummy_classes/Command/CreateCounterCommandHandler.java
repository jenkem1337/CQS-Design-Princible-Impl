package dummy_classes.Command;

import dummy_classes.Command.CreateCounterCommand;
import dummy_classes.CounterDao;
import dummy_classes.Model.Counter;
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
