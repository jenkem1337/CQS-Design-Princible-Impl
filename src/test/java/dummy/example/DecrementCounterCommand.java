package dummy.example;

import org.cqs.Command;

public class DecrementCounterCommand implements Command {
    private String hashKey;

    public String getHashKey() {
        return hashKey;
    }

    public DecrementCounterCommand(String hashKey) {
        this.hashKey = hashKey;
    }
}
