package com.kubernetes.client.dsl;

/**
 * 级联
 *
 * @param <T>
 */
public interface Cascading<T> {

    @Deprecated
    T cascading(boolean enabled);
}
