package com.kubernetes.client.dsl;

/**
 * 操作
 *
 * @param <T>
 * @param <L>
 * @param <R>
 */
public interface Operation<T, L, R>
        extends
        AnyNamespaceable<FilterWatchListMultiDeletable<T, L>>,
        Namespaceable<NonNamespaceOperation<T, L, R>>,
        FilterWatchListMultiDeletable<T, L>,
        Loadable<R> {
}
