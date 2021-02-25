package com.kubernetes.client.dsl;

/**
 * create 操作
 *
 * @param <T>
 */
public interface Createable<T> {

    T create(T... item);

    T create(T item);
}
