package com.kubernetes.client;

import com.kubernetes.client.dsl.ApiextensionsAPIGroupDSL;
import com.kubernetes.client.util.Serialization;
import okhttp3.OkHttpClient;

import java.io.InputStream;

/**
 * k8s client
 */
public class DefaultKubernetesClient extends BaseClient implements NamespacedKubernetesClient {

    public DefaultKubernetesClient() {
        super();
    }

    public DefaultKubernetesClient(Config config) {
        super(config);
    }


    public DefaultKubernetesClient(OkHttpClient httpClient, Config config) {
        super(httpClient, config);
    }

    public static DefaultKubernetesClient fromConfig(String config) {
        return new DefaultKubernetesClient(Serialization.unmarshal(config, Config.class));
    }

    public static DefaultKubernetesClient fromConfig(InputStream is) {
        return new DefaultKubernetesClient(Serialization.unmarshal(is, Config.class));
    }


    @Override
    public ApiextensionsAPIGroupDSL apiextensions() {

        return adapt(ApiextensionsAPIGroupClient.class);
    }
}
