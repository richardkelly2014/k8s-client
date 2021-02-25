package com.kubernetes.client.dsl;

/**
 * 状态跟新操作
 *
 * @param <T>
 */
public interface StatusUpdatable<T> {
    T updateStatus(T item);
}
