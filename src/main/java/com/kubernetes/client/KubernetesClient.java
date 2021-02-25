package com.kubernetes.client;

import com.kubernetes.client.dsl.ApiextensionsAPIGroupDSL;

/**
 * k8s main client
 */
public interface KubernetesClient extends Client {

    /**
     * api extensions api group /v1 /v1beta1
     *
     * @return
     */
    ApiextensionsAPIGroupDSL apiextensions();
}
