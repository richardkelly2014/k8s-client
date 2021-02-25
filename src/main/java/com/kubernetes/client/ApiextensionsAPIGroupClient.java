package com.kubernetes.client;

import com.kubernetes.client.dsl.ApiextensionsAPIGroupDSL;
import com.kubernetes.client.dsl.V1ApiextensionAPIGroupDSL;
import com.kubernetes.client.dsl.V1beta1ApiextensionAPIGroupDSL;
import okhttp3.OkHttpClient;

/**
 * api 扩展 group
 */
public class ApiextensionsAPIGroupClient extends BaseClient implements ApiextensionsAPIGroupDSL {

    public ApiextensionsAPIGroupClient() {
        super();
    }

    public ApiextensionsAPIGroupClient(OkHttpClient httpClient, final Config config) {
        super(httpClient, config);
    }


    @Override
    public V1ApiextensionAPIGroupDSL v1() {
        return null;
    }

    @Override
    public V1beta1ApiextensionAPIGroupDSL v1beta1() {
        return null;
    }
}
