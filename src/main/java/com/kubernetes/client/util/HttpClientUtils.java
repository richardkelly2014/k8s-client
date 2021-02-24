package com.kubernetes.client.util;

import com.kubernetes.client.Config;
import com.kubernetes.client.KubernetesClientException;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;

import java.util.Collections;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * http client ok http
 */
public class HttpClientUtils {
    private HttpClientUtils() {
    }

    private static Pattern VALID_IPV4_PATTERN = null;
    public static final String ipv4Pattern = "(http:\\/\\/|https:\\/\\/)?(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])(\\/[0-9]\\d|1[0-9]\\d|2[0-9]\\d|3[0-2]\\d)?";
    protected static final String KUBERNETES_BACKWARDS_COMPATIBILITY_INTERCEPTOR_DISABLE = "kubernetes.backwardsCompatibilityInterceptor.disable";

    static {
        try {
            VALID_IPV4_PATTERN = Pattern.compile(ipv4Pattern, Pattern.CASE_INSENSITIVE);
        } catch (PatternSyntaxException e) {
            throw KubernetesClientException.launderThrowable("Unable to compile ipv4address pattern.", e);
        }
    }

    public static OkHttpClient createHttpClient(final Config config) {
        return createHttpClient(config, (b) -> {
        });
    }

    public static OkHttpClient createHttpClientForMockServer(final Config config) {
        return createHttpClient(config, b -> b.protocols(Collections.singletonList(Protocol.HTTP_1_1)));
    }


    private static OkHttpClient createHttpClient(final Config config, final Consumer<OkHttpClient.Builder> additionalConfig) {

        return null;
    }

    /**
     * 是否是ip地址
     *
     * @param ipAddress
     * @return
     */
    private static boolean isIpAddress(String ipAddress) {
        Matcher ipMatcher = VALID_IPV4_PATTERN.matcher(ipAddress);
        return ipMatcher.matches();
    }

    /**
     * 1.8 禁用 http2
     *
     * @return
     */
    private static boolean shouldDisableHttp2() {
        return System.getProperty("java.version", "").startsWith("1.8");
    }
}
