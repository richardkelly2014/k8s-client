package com.kubernetes.client.dsl;

/**
 * 版本 观察等待
 *
 * @param <T>
 */
public interface VersionWatchAndWaitable<T> extends
        WatchAndWaitable<T>, Versionable<WatchAndWaitable<T>> {
}
