package com.kubernetes.client.dsl;

import com.kubernetes.client.Client;

/**
 * API entrypoint for apiextensions resources v1 / v1beta1
 */
public interface ApiextensionsAPIGroupDSL extends Client {
    V1ApiextensionAPIGroupDSL v1();

    V1beta1ApiextensionAPIGroupDSL v1beta1();
}
