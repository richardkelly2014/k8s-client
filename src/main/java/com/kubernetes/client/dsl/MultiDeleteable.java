package com.kubernetes.client.dsl;

import java.util.List;

/**
 * 多个删除操作
 *
 * @param <T>
 */
public interface MultiDeleteable<T> {
    Boolean delete(T... items);

    Boolean delete(List<T> items);
}
