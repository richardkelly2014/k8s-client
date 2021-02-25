package com.kubernetes.client.dsl;

public interface Namespaceable<T> {

    T inNamespace(String name);
}
