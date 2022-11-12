package org.cqs.impl;

import org.cqs.Query;
import org.cqs.RequestStrategy;

public class QueryRequestStrategy extends BaseDispatcherMediatorComponent implements RequestStrategy<Query> {
    @Override
    public Object dispatch(Query obj) throws Exception {
        return queryDispatcherMediator.handle(obj);
    }
}
