package com.kubernetes.client;

import com.kubernetes.api.model.apiextensions.v1beta1.CustomResourceDefinition;
import com.kubernetes.api.model.apiextensions.v1beta1.CustomResourceDefinitionList;
import com.kubernetes.client.dsl.MixedOperation;
import com.kubernetes.client.dsl.Resource;
import com.kubernetes.client.dsl.V1beta1ApiextensionAPIGroupDSL;
import okhttp3.OkHttpClient;

public class V1beta1ApiextensionsAPIGroupClient extends BaseClient implements V1beta1ApiextensionAPIGroupDSL {

    public V1beta1ApiextensionsAPIGroupClient() {
        super();
    }

    public V1beta1ApiextensionsAPIGroupClient(OkHttpClient httpClient, final Config config) {
        super(httpClient, config);
    }

    public MixedOperation<CustomResourceDefinition,
            CustomResourceDefinitionList,
            Resource<CustomResourceDefinition>> customResourceDefinitions() {
        return new CustomResourceDefinitionOperationsImpl(httpClient, getConfiguration());
    }
}
