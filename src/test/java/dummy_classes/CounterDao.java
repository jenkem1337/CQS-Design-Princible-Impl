package dummy_classes;

import dummy_classes.Model.Counter;

public interface CounterDao {
    Counter getCounter(String key) throws Exception;
    void persistCounter(String key, Counter counter);
}
