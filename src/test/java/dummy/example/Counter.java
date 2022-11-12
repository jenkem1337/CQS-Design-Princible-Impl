package dummy.example;

public class Counter {
    private Integer _number;

    public Counter() {
        _number = 0;
    }

    public Counter(Integer integer){
        _number = integer;
    }

    public void increment(){
        _number++;
    }


    public void decrement() {
        _number--;
    }

    public Integer getNumber(){
        return _number;
    }
}