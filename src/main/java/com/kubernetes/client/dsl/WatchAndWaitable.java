package com.kubernetes.client.dsl;

import com.kubernetes.client.Watcher;

public interface WatchAndWaitable<T> extends Watchable<Watcher<T>>, Waitable<T, T> {
}
