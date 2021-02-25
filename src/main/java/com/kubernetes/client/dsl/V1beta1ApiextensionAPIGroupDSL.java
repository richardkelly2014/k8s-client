package com.kubernetes.client.dsl;

import com.kubernetes.api.model.apiextensions.v1beta1.CustomResourceDefinition;
import com.kubernetes.api.model.apiextensions.v1beta1.CustomResourceDefinitionList;
import com.kubernetes.client.Client;

public interface V1beta1ApiextensionAPIGroupDSL extends Client {

    NonNamespaceOperation<CustomResourceDefinition,
            CustomResourceDefinitionList,
            Resource<CustomResourceDefinition>> customResourceDefinitions();
}
