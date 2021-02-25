package com.kubernetes.client.dsl;

public interface NonNamespaceOperation<T, L, R> extends
        Nameable<R>,
        FilterWatchListMultiDeletable<T, L>,
        Createable<T>,
        CreateOrReplaceable<T>,
        Loadable<R> {
}