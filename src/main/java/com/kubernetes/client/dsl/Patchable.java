package com.kubernetes.client.dsl;

public interface Patchable<T> {
    T patch(T item);
}
