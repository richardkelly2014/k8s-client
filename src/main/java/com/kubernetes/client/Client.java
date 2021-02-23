package com.kubernetes.client;

import java.io.Closeable;

/**
 * Client Interface
 */
public interface Client extends ConfigAware, Closeable {

    /**
     * 检测是否适应其他客户端
     *
     * @param type
     * @param <C>
     * @return
     */
    <C> Boolean isAdaptable(Class<C> type);
}
