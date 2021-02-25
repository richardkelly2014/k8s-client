package com.kubernetes.client.dsl;

/**
 * 观察 过滤 删除列表
 *
 * @param <T>
 * @param <L>
 */
public interface FilterWatchListDeletable<T, L> extends
        Filterable<FilterWatchListDeletable<T, L>>, WatchListDeletable<T, L> {

}
