package dummy_classes.Queries;

import org.cqs.Query;

public class GetACounterQuery implements Query {
    private String hashKey;

    public String getHashKey() {
        return hashKey;
    }

    public GetACounterQuery(String hashKey) {
        this.hashKey = hashKey;
    }
}
