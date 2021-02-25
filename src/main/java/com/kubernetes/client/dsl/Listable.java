package com.kubernetes.client.dsl;

import com.kubernetes.api.model.ListOptions;

/**
 * list 操作
 *
 * @param <T>
 */
public interface Listable<T> {

    T list();

    @Deprecated
    T list(Integer limitVal, String continueVal);

    T list(ListOptions listOptions);

}
