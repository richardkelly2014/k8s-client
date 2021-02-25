package com.kubernetes.client.dsl;

public interface AnyNamespaceable<T> {

    T inAnyNamespace();
}
