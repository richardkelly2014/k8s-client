package com.kubernetes.client.dsl;

import com.kubernetes.api.model.certificates.v1beta1.CertificateSigningRequest;
import com.kubernetes.api.model.certificates.v1beta1.CertificateSigningRequestList;
import com.kubernetes.client.Client;

public interface V1beta1CertificatesAPIGroupDSL extends Client {

    NonNamespaceOperation<CertificateSigningRequest,
            CertificateSigningRequestList,
            Resource<CertificateSigningRequest>> certificateSigningRequests();
}
