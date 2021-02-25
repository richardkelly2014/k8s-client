package com.kubernetes.client.dsl;

import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

public interface Waitable<T, P> {

    long DEFAULT_INITIAL_BACKOFF_MILLIS = 5L;
    double DEFAULT_BACKOFF_MULTIPLIER = 2d;

    T waitUntilReady(long amount, TimeUnit timeUnit) throws InterruptedException;

    T waitUntilCondition(Predicate<P> condition, long amount, TimeUnit timeUnit) throws InterruptedException;

    /**
     * Configure the backoff strategy to use when waiting for conditions, in case the watcher encounters a retryable error.
     * @param initialBackoff the value for the initial backoff on first error
     * @param backoffUnit the TimeUnit for the initial backoff value
     * @param backoffMultiplier what to multiply the backoff by on each subsequent error
     * @return the waitable
     */
    Waitable<T, P> withWaitRetryBackoff(long initialBackoff, TimeUnit backoffUnit, double backoffMultiplier);
}
