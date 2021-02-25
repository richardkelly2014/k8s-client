package com.kubernetes.client;

import com.kubernetes.client.dsl.NonNamespaceOperation;

public interface V1ApiextensionAPIGroupDSL extends Client {

    /**
     * 自定义资源
     *
     * @return
     */
    NonNamespaceOperation customResourceDefinitions();
}
