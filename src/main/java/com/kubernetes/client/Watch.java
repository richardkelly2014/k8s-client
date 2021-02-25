package com.kubernetes.client;

import java.io.Closeable;

/**
 * 观察
 */
public interface Watch extends Closeable {

    /**
     * Close the Watch.
     */
    void close();

}
