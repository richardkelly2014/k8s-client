package com.kubernetes.client.util;

import com.kubernetes.client.Config;
import com.kubernetes.client.RequestConfig;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.kubernetes.client.util.Utils.isNotNullOrEmpty;

/**
 * 模拟登录
 */
public class ImpersonatorInterceptor implements Interceptor {

    private final Config config;

    public ImpersonatorInterceptor(Config config) {
        this.config = config;
    }

    /**
     * 将 requestConfig 参数加入到 http header
     *
     * @param chain
     * @return
     * @throws IOException
     */
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        RequestConfig requestConfig = config.getRequestConfig();
        if (isNotNullOrEmpty(requestConfig.getImpersonateUsername())) {
            Request.Builder requestBuilder = chain.request().newBuilder();

            requestBuilder.addHeader("Impersonate-User", requestConfig.getImpersonateUsername());

            String[] impersonateGroups = requestConfig.getImpersonateGroups();
            if (isNotNullOrEmpty(impersonateGroups)) {
                for (String group : impersonateGroups) {
                    requestBuilder.addHeader("Impersonate-Group", group);
                }
            }

            Map<String, List<String>> impersonateExtras = requestConfig.getImpersonateExtras();
            if (isNotNullOrEmpty(impersonateExtras)) {
                Collection<?> keys = impersonateExtras.keySet();
                for (Object key : keys) {
                    List<String> values = impersonateExtras.get(key);
                    if (values != null) {
                        for (String value : values) {
                            requestBuilder.addHeader("Impersonate-Extra-" + key, value);
                        }
                    }
                }
            }

            request = requestBuilder.build();
        }
        return chain.proceed(request);
    }
}
