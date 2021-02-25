package com.kubernetes.client.dsl;

/**
 * 版本操作
 *
 * @param <T>
 */
public interface Versionable<T> {

    T withResourceVersion(String resourceVersion);
}
