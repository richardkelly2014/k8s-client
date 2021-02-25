package com.kubernetes.client.dsl;

/**
 * 创建 or 替换
 *
 * @param <T>
 */
public interface CreateOrReplaceable<T> {
    T createOrReplace(T... item);
}
