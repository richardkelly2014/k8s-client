package com.kubernetes.client.dsl;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

/**
 * 加载操作
 *
 * @param <T>
 */
public interface Loadable<T> {
    /**
     * Load from an {@link InputStream}.
     * @param is  The input stream.
     * @return returns de-serialized object
     */
    T load(InputStream is);

    /**
     * Load from a {@link URL}.
     * @param url  The url.
     * @return returns de-serialized object
     */
    T load(URL url);

    /**
     * Load from a {@link File}.
     * @param file  The file.
     * @return returns de-serialized object
     */
    T load(File file);

    /**
     * Load from path.
     * @param path  The path.
     * @return returns de-serialized object
     */
    T load(String path);
}
