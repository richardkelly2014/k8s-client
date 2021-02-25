package com.kubernetes.client;

import com.kubernetes.client.dsl.NonNamespaceOperation;

public interface V1beta1ApiextensionAPIGroupDSL extends Client {

    NonNamespaceOperation customResourceDefinitions();
}
