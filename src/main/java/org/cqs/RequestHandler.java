package org.cqs;

public interface RequestHandler<K, V> {
    V handle(K obj) throws Exception;
}
