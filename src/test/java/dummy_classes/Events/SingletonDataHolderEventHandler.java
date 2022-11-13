package dummy_classes.Events;

import dummy_classes.SingletonDataHolder;
import org.cqs.EventHandler;

public class SingletonDataHolderEventHandler implements EventHandler<CounterIncremented> {
    @Override
    public Void handle(CounterIncremented obj) throws Exception {
        var singletonDataHolder = SingletonDataHolder.getInstance();
        singletonDataHolder.setData(obj.getCounter().getNumber());
        return null;
    }
}
