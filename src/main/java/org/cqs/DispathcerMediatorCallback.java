package org.cqs;

@FunctionalInterface
public interface DispathcerMediatorCallback {
    void apply(CommandDispatcherMediator commandDispatcherMediator, QueryDispatcherMediator queryDispatcherMediator);
}
