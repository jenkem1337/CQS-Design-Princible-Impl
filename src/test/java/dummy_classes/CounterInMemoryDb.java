package dummy_classes;

import dummy_classes.Model.Counter;

import java.util.HashMap;
import java.util.Map;

public class CounterInMemoryDb implements CounterDao{
    Map<String, Counter> counterHashMap  = new HashMap<String, Counter>() {{
        put("counter1", new Counter());

    }};

    @Override
    public Counter getCounter(String key) throws Exception {
        if(!counterHashMap.containsKey(key)){
            throw new Exception("Counter doesn't exist");
        }
        return counterHashMap.get(key);
    }

    @Override
    public void persistCounter(String key, Counter counter) {
        counterHashMap.put(key, counter);
    }
}
