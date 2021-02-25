package com.kubernetes.client.dsl;

/**
 * 多观察 删除
 *
 * @param <T>
 * @param <L>
 */
public interface FilterWatchListMultiDeletable<T, L> extends
        FilterWatchListDeletable<T, L>, MultiDeleteable<T> {

}
