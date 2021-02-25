package com.kubernetes.client;

/**
 * 可配置
 *
 * @param <T>
 */
public interface GracePeriodConfigurable<T> {
    T withGracePeriod(long gracePeriodSeconds);
}
