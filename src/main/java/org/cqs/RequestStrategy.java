package org.cqs;

public interface RequestStrategy<T> {
    Object dispatch(T obj) throws Exception;
}
