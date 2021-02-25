package com.kubernetes.client;

import com.kubernetes.api.model.RootPaths;
import com.kubernetes.client.util.HttpClientUtils;
import com.kubernetes.client.util.Utils;

import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;

import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
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

    @Override
    public void close() {
        ConnectionPool connectionPool = httpClient.connectionPool();
        Dispatcher dispatcher = httpClient.dispatcher();
        ExecutorService executorService = httpClient.dispatcher() != null ? httpClient.dispatcher().executorService() : null;

        if (dispatcher != null) {
            dispatcher.cancelAll();
        }

        if (connectionPool != null) {
            connectionPool.evictAll();
        }

        Utils.shutdownExecutorService(executorService);
    }

    @Override
    public URL getMasterUrl() {
        return masterUrl;
    }

    @Override
    public String getApiVersion() {
        return apiVersion;
    }

    @Override
    public String getNamespace() {
        return namespace;
    }


    @Override
    public Config getConfiguration() {
        return configuration;
    }

    @Override
    public OkHttpClient getHttpClient() {
        return httpClient;
    }

    @Override
    public <C> Boolean isAdaptable(Class<C> type) {
        ExtensionAdapter<C> adapter = Adapters.get(type);
        if (adapter != null) {
            return adapter.isAdaptable(this);
        } else {
            return false;
        }
    }

    @Override
    public <C> C adapt(Class<C> type) {
        ExtensionAdapter<C> adapter = Adapters.get(type);
        if (adapter != null) {
            return adapter.adapt(this);
        }
        throw new IllegalStateException("No adapter available for type:" + type);
    }

    @Override
    public RootPaths rootPaths() {
//        return new BaseOperation(new OperationContext().withOkhttpClient(httpClient).withConfig(configuration)) {
//        }.getRootPaths();
        return null;
    }

    @Override
    public boolean supportsApiPath(String apiPath) {
        RootPaths rootPaths = rootPaths();
        if (rootPaths != null) {
            List<String> paths = rootPaths.getPaths();
            if (paths != null) {
                for (String path : paths) {
                    if (path.equals(apiPath)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
