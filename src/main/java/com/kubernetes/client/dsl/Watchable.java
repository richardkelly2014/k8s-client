package com.kubernetes.client.dsl;

import com.kubernetes.api.model.ListOptions;
import com.kubernetes.client.Watch;

/**
 * 观察操作
 *
 * @param <W>
 */
public interface Watchable<W> {

    /**
     * Watch returns {@link Watch} interface that watches requested resource
     *
     * @param watcher Watcher interface of Kubernetes resource
     * @return watch interface {@link Watch}
     */
    Watch watch(W watcher);

    /**
     * Watch returns {@link Watch} interface that watches requested resource
     *
     * @param options options available for watch operation
     * @param watcher Watcher interface of Kubernetes resource
     * @return watch interface {@link Watch}
     */
    Watch watch(ListOptions options, W watcher);

    /**
     * Watch returns {@link Watch} interface that watches requested resource from specified
     * resourceVersion
     *
     * @param resourceVersion resource version from where to start watch
     * @param watcher         Watcher interface of Kubernetes resource
     * @return watch interface {@link Watch}
     * @deprecated Please use {@link #watch(ListOptions, Object)} instead, it has a parameter of
     * resourceVersion
     */
    @Deprecated
    Watch watch(String resourceVersion, W watcher);

}
