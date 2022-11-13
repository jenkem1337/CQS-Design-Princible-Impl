package dummy_classes.Command;

import org.cqs.Command;

public class IncrementCounterCommand implements Command {
    private String hashKey;

    public String getHashKey() {
        return hashKey;
    }

    public IncrementCounterCommand(String key){
        hashKey = key;
    }
}
