package com.kubernetes.client;

import com.kubernetes.client.util.HttpClientUtils;
import com.kubernetes.client.util.Utils;

import java.net.URL;

import okhttp3.OkHttpClient;

/**
 * base client
 */
public abstract class BaseClient implements Client, HttpClientAware {

    protected OkHttpClient httpClient;
    private URL masterUrl;
    private String apiVersion;
    private String namespace;
    private Config configuration;

    public BaseClient() {
        //自动加载 config
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
