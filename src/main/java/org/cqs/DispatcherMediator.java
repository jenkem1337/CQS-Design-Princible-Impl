package org.cqs;

public interface DispatcherMediator<T, K> {
    Object handle(T obj) throws Exception;
    DispatcherMediator addHandler(String key, K handler);
}
