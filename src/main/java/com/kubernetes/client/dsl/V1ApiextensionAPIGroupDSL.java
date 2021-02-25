package com.kubernetes.client.dsl;

import com.kubernetes.api.model.apiextensions.v1.CustomResourceDefinition;
import com.kubernetes.api.model.apiextensions.v1.CustomResourceDefinitionList;
import com.kubernetes.client.Client;

public interface V1ApiextensionAPIGroupDSL extends Client {

    /**
     * 自定义资源
     *
     * @return
     */
    NonNamespaceOperation<CustomResourceDefinition,
            CustomResourceDefinitionList,
            Resource<CustomResourceDefinition>> customResourceDefinitions();
}
