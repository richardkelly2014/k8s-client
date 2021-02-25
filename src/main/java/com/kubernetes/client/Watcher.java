package com.kubernetes.client;

import org.slf4j.LoggerFactory;

/**
 * 观察
 *
 * @param <T>
 */
public interface Watcher<T> {

    void eventReceived(Action action, T resource);

    /**
     * Invoked when the watcher is gracefully closed.
     */
    default void onClose() {
        LoggerFactory.getLogger(Watcher.class).debug("Watcher closed");
    }

    /**
     * Invoked when the watcher closes due to an Exception.
     *
     * @param cause What caused the watcher to be closed.
     */
    void onClose(WatcherException cause);

    enum Action {
        ADDED, MODIFIED, DELETED, ERROR
    }

}
