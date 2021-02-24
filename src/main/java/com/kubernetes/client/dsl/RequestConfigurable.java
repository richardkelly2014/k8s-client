package com.kubernetes.client.dsl;

import com.kubernetes.client.Client;
import com.kubernetes.client.RequestConfig;

/**
 * request config 配置
 * @param <C>
 */
public interface RequestConfigurable<C extends Client> {

    FunctionCallable<C> withRequestConfig(RequestConfig requestConfig);
}
