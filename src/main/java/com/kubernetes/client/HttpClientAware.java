package com.kubernetes.client;

import okhttp3.OkHttpClient;

/**
 * http client
 */
public interface HttpClientAware {

    OkHttpClient getHttpClient();
}
