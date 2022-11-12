package dummy.example;

public interface CounterDao {
    Counter getCounter(String key) throws Exception;
    void persistCounter(String key, Counter counter);
}
