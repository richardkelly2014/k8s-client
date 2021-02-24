package com.kubernetes.client;

import com.kubernetes.api.model.RootPaths;

import java.io.Closeable;
import java.net.URL;

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

    <C> C adapt(Class<C> type);

    URL getMasterUrl();

    String getApiVersion();

    String getNamespace();

    RootPaths rootPaths();

    boolean supportsApiPath(String path);

    void close();
}
