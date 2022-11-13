package dummy_classes.Queries;

import dummy_classes.CounterDao;
import dummy_classes.Model.Counter;
import org.cqs.QueryHandler;

public class GetSingleCounterQueryHandler implements QueryHandler<GetACounterQuery, Counter> {
    private CounterDao counterDao;
    public GetSingleCounterQueryHandler(CounterDao counterDao){
        this.counterDao = counterDao;
    }
    @Override
    public Counter handle(GetACounterQuery obj) throws Exception {
        return counterDao.getCounter(obj.getHashKey());
    }
}
