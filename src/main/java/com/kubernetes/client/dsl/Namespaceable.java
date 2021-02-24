package com.kubernetes.client.dsl;

/**
 * 命名空间
 *
 * @param <T>
 */
public interface Namespaceable<T> {

    T inNamespace(String name);
}
