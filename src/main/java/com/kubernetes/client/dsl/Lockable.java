package com.kubernetes.client.dsl;

/**
 * 锁操作
 *
 * @param <T>
 */
public interface Lockable<T> {

    T lockResourceVersion(String resourceVersion);
}
