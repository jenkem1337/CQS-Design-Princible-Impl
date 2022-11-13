package org.cqs.impl;

import org.cqs.*;

import java.util.HashMap;
import java.util.Map;

public class DispatcherContextFactory {
    private RequestStrategy<Command> commandRequestStrategy;
    private RequestStrategy<Query>  queryRequestStrategy;
    private RequestStrategy<Event> eventRequestStrategy;

    private CommandDispatcherMediator commandDispatcherMediator;
    private QueryDispatcherMediator queryDispatcherMediator;
    private EventDispatcherMediator eventDispatcherMediator;
    private Map<String, RequestStrategy> dispatcherContextHashMap = new HashMap<>();
    public DispatcherContextFactory(DispathcerMediatorCallback callback) {
        commandRequestStrategy = new CommandRequestStrategy();
        queryRequestStrategy   = new QueryRequestStrategy();

        commandDispatcherMediator = new CommandDispatcherMediatorImpl((BaseDispatcherMediatorComponent) commandRequestStrategy);
        queryDispatcherMediator   = new QueryDispatcherMediatorImpl((BaseDispatcherMediatorComponent) queryRequestStrategy);

        callback.apply(commandDispatcherMediator, queryDispatcherMediator);

        dispatcherContextHashMap.put(Command.class.getSimpleName(), commandRequestStrategy);
        dispatcherContextHashMap.put(Query.class.getSimpleName(), queryRequestStrategy);
    }

    public DispatcherContext createAnInstanceOfDispatcherContext() {
        return new DispatcherContext(dispatcherContextHashMap);
    }
}
