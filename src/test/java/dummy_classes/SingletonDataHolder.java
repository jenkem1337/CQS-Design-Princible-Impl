package dummy_classes;

public class SingletonDataHolder {
    private static SingletonDataHolder singletonDataHolder;
    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static SingletonDataHolder getInstance() {
        if(singletonDataHolder == null){
            singletonDataHolder = new SingletonDataHolder();
        }
        return singletonDataHolder;
    }
}
