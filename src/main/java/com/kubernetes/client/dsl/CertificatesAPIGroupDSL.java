package com.kubernetes.client.dsl;

import com.kubernetes.client.Client;

/**
 * 证书 api 接口
 */
public interface CertificatesAPIGroupDSL extends Client {
    V1CertificatesAPIGroupDSL v1();

    V1beta1CertificatesAPIGroupDSL v1beta1();
}
