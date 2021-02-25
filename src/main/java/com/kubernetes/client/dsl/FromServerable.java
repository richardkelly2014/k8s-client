package com.kubernetes.client.dsl;

/**
 * 服务器
 *
 * @param <T>
 */
public interface FromServerable<T> {

    T fromServer();
}
