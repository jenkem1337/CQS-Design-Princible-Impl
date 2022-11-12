package org.cqs.impl;

import org.cqs.Command;
import org.cqs.Query;
import org.cqs.RequestStrategy;

import java.util.Map;

public class DispatcherContext {
    private Map<String, RequestStrategy> requestStrategyHashMap;

    public DispatcherContext(Map<String, RequestStrategy> requestStrategyHashMap) {
        this.requestStrategyHashMap = requestStrategyHashMap;
    }

    public <R ,T> R send(T obj) throws Exception {
        if(!requestStrategyHashMap.containsKey(obj.getClass().getInterfaces()[0].getSimpleName())) {
            throw new Exception("Your key doesnt exist in hash map");
        }
        RequestStrategy requestStrategy = requestStrategyHashMap.get(obj.getClass().getInterfaces()[0].getSimpleName());

        return (R) requestStrategy.dispatch(obj);
    }

}
