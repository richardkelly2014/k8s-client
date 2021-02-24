package com.kubernetes.client.dsl;

/**
 * 命名空间
 *
 * @param <T>
 */
public interface AnyNamespaceable<T> {

    T inAnyNamespace();
}
