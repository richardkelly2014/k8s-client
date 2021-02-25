package com.kubernetes.client.dsl;

/**
 * 名称
 *
 * @param <T>
 */
public interface Nameable<T> {

    T withName(String name);
}
