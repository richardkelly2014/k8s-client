package com.kubernetes.client.util;

import com.kubernetes.api.model.AuthInfo;
import com.kubernetes.api.model.Context;
import com.kubernetes.client.Config;
import com.kubernetes.client.internal.KubeConfigUtils;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * token 刷新
 */
public class TokenRefreshInterceptor implements Interceptor {

    private Config config;

    public TokenRefreshInterceptor(Config config) {
        this.config = config;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);

        if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
            //读取本地~.kube/config
            com.kubernetes.api.model.Config kubeConfig = KubeConfigUtils.parseConfig(new File(Config.getKubeconfigFilename()));
            Context currentContext = null;
            String currentContextName = null;
            //client Config
            if (config.getCurrentContext() != null) {
                currentContext = config.getCurrentContext().getContext();
                currentContextName = config.getCurrentContext().getName();
            }
            //当前用户信息
            AuthInfo currentAuthInfo = KubeConfigUtils.getUserAuthInfo(kubeConfig, currentContext);
            // Check if AuthProvider is set or not
            if (currentAuthInfo != null && currentAuthInfo.getAuthProvider() != null) {
                response.close();
                String newAccessToken;
                if (currentAuthInfo.getAuthProvider().getName().toLowerCase().equals("oidc")) {
                    newAccessToken = OpenIDConnectionUtils.resolveOIDCTokenFromAuthConfig(currentAuthInfo.getAuthProvider().getConfig());
                } else {
                    //重新读取 config
                    Config newestConfig = Config.autoConfigure(currentContextName);
                    newAccessToken = newestConfig.getOauthToken();
                }
                // Delete old Authorization header and append new one
                Request authReqWithUpdatedToken = chain.request().newBuilder()
                        .header("Authorization", "Bearer " + newAccessToken).build();
                config.setOauthToken(newAccessToken);
                return chain.proceed(authReqWithUpdatedToken);
            }
        }
        return response;
    }
}
