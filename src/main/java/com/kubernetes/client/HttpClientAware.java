package com.kubernetes.client;

import okhttp3.OkHttpClient;

/**
 * httpclient 配置
 * (OkHttpClient)
 */
public interface HttpClientAware {
    OkHttpClient getHttpClient();
}
