package com.kubernetes.client;

import com.kubernetes.api.model.apiextensions.v1.CustomResourceDefinition;
import com.kubernetes.api.model.apiextensions.v1.CustomResourceDefinitionList;

import com.kubernetes.client.dsl.MixedOperation;
import com.kubernetes.client.dsl.Resource;
import com.kubernetes.client.dsl.V1ApiextensionAPIGroupDSL;
import okhttp3.OkHttpClient;

/**
 *
 */
public class V1ApiextensionsAPIGroupClient extends BaseClient implements V1ApiextensionAPIGroupDSL {

    public V1ApiextensionsAPIGroupClient() {
        super();
    }

    public V1ApiextensionsAPIGroupClient(OkHttpClient httpClient, final Config config) {
        super(httpClient, config);
    }

    @Override
    public MixedOperation<CustomResourceDefinition,
            CustomResourceDefinitionList,
            Resource<CustomResourceDefinition>> customResourceDefinitions() {
        return null;
    }
}
