package dummy_classes.Command;

import org.cqs.Command;

public class CreateCounterCommand implements Command {
    private Integer _integer;
    private String _hashKey;

    public CreateCounterCommand(String hashKey,Integer integer) {
        _integer = integer;
        _hashKey = hashKey;
    }

    public String get_hashKey() {
        return _hashKey;
    }

    public Integer getNumber(){
        return _integer;
    }
}
