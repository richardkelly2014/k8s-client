package com.kubernetes.client;

import com.kubernetes.client.util.HttpClientUtils;
import com.kubernetes.client.util.Utils;
import okhttp3.OkHttpClient;

import java.net.URL;

/**
 * base client
 */
public abstract class BaseClient implements Client, HttpClientAware {

    //http request
    protected OkHttpClient httpClient;
    //server url
    private URL masterUrl;
    //version
    private String apiVersion;
    //命名空间
    private String namespace;
    //config 配置
    private Config configuration;

    public BaseClient() {

        this(Config.autoConfigure(null));
    }

    public BaseClient(final Config config) {

        this(HttpClientUtils.createHttpClient(config), config);
    }

    public BaseClient(final OkHttpClient httpClient, Config config) {
        try {
            this.httpClient = httpClient;
            this.namespace = config.getNamespace();
            this.configuration = config;
            this.apiVersion = config.getApiVersion();
            if (config.getMasterUrl() == null) {
                throw new KubernetesClientException("Unknown Kubernetes master URL - " +
                        "please set with the builder, or set with either system property \"" + Config.KUBERNETES_MASTER_SYSTEM_PROPERTY + "\"" +
                        " or environment variable \"" + Utils.convertSystemPropertyNameToEnvVar(Config.KUBERNETES_MASTER_SYSTEM_PROPERTY) + "\"");
            }
            this.masterUrl = new URL(config.getMasterUrl());
        } catch (Exception e) {
            throw KubernetesClientException.launderThrowable(e);
        }
    }
}
