package org.cqs.impl;

import org.cqs.DispatcherMediator;
import org.cqs.Query;
import org.cqs.QueryDispatcherMediator;
import org.cqs.QueryHandler;

import java.util.HashMap;
import java.util.Map;

public class QueryDispatcherMediatorImpl implements QueryDispatcherMediator {
    private Map<String, QueryHandler> queryHandlerHashMap = new HashMap<>();

    public QueryDispatcherMediatorImpl(BaseDispatcherMediatorComponent component){
        component.setQueryDispatcherMediator(this);
    }
    @Override
    public Object handle(Query obj) throws Exception {
        if(!queryHandlerHashMap.containsKey(obj.getClass().getSimpleName())) {
            throw new Exception("This query handler doesnt exist");
        }
        QueryHandler<Query, ?> _queryHandler = queryHandlerHashMap.get(obj.getClass().getSimpleName());

        return _queryHandler.handle(obj);
    }

    @Override
    public DispatcherMediator addHandler(String key, QueryHandler handler) {
        queryHandlerHashMap.put(key, handler);
        return this;
    }
}
