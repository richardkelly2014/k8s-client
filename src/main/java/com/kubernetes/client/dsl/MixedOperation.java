package com.kubernetes.client.dsl;

/**
 * 混合操作
 * @param <T>
 * @param <L>
 * @param <R>
 */
public interface MixedOperation<T, L, R extends Resource<T>>
        extends Operation<T, L, R>,
        NonNamespaceOperation<T, L, R> {
}
