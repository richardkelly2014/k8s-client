package com.kubernetes.client;

/**
 * Config 配置
 * @param <C>
 */
public interface ConfigAware<C extends Config> {

    C getConfiguration();
}
