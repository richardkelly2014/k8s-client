package com.kubernetes.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kubernetes.api.model.NamedContext;
import com.kubernetes.client.internal.KubeConfigUtils;
import com.kubernetes.client.util.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.TlsVersion;

import static okhttp3.TlsVersion.TLS_1_2;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class Config {
    private static final Logger LOGGER = LoggerFactory.getLogger(Config.class);

    public static final String KUBERNETES_DISABLE_AUTO_CONFIG_SYSTEM_PROPERTY = "kubernetes.disable.autoConfig";
    public static final String KUBERNETES_MASTER_SYSTEM_PROPERTY = "kubernetes.master";
    public static final String KUBERNETES_API_VERSION_SYSTEM_PROPERTY = "kubernetes.api.version";
    public static final String KUBERNETES_TRUST_CERT_SYSTEM_PROPERTY = "kubernetes.trust.certificates";
    public static final String KUBERNETES_DISABLE_HOSTNAME_VERIFICATION_SYSTEM_PROPERTY = "kubernetes.disable.hostname.verification";
    public static final String KUBERNETES_CA_CERTIFICATE_FILE_SYSTEM_PROPERTY = "kubernetes.certs.ca.file";
    public static final String KUBERNETES_CA_CERTIFICATE_DATA_SYSTEM_PROPERTY = "kubernetes.certs.ca.data";
    public static final String KUBERNETES_CLIENT_CERTIFICATE_FILE_SYSTEM_PROPERTY = "kubernetes.certs.client.file";
    public static final String KUBERNETES_CLIENT_CERTIFICATE_DATA_SYSTEM_PROPERTY = "kubernetes.certs.client.data";
    public static final String KUBERNETES_CLIENT_KEY_FILE_SYSTEM_PROPERTY = "kubernetes.certs.client.key.file";
    public static final String KUBERNETES_CLIENT_KEY_DATA_SYSTEM_PROPERTY = "kubernetes.certs.client.key.data";
    public static final String KUBERNETES_CLIENT_KEY_ALGO_SYSTEM_PROPERTY = "kubernetes.certs.client.key.algo";
    public static final String KUBERNETES_CLIENT_KEY_PASSPHRASE_SYSTEM_PROPERTY = "kubernetes.certs.client.key.passphrase";
    public static final String KUBERNETES_AUTH_BASIC_USERNAME_SYSTEM_PROPERTY = "kubernetes.auth.basic.username";
    public static final String KUBERNETES_AUTH_BASIC_PASSWORD_SYSTEM_PROPERTY = "kubernetes.auth.basic.password";
    public static final String KUBERNETES_AUTH_TRYKUBECONFIG_SYSTEM_PROPERTY = "kubernetes.auth.tryKubeConfig";
    public static final String KUBERNETES_AUTH_TRYSERVICEACCOUNT_SYSTEM_PROPERTY = "kubernetes.auth.tryServiceAccount";
    public static final String KUBERNETES_OAUTH_TOKEN_SYSTEM_PROPERTY = "kubernetes.auth.token";
    public static final String KUBERNETES_WATCH_RECONNECT_INTERVAL_SYSTEM_PROPERTY = "kubernetes.watch.reconnectInterval";
    public static final String KUBERNETES_WATCH_RECONNECT_LIMIT_SYSTEM_PROPERTY = "kubernetes.watch.reconnectLimit";
    public static final String KUBERNETES_CONNECTION_TIMEOUT_SYSTEM_PROPERTY = "kubernetes.connection.timeout";
    public static final String KUBERNETES_REQUEST_TIMEOUT_SYSTEM_PROPERTY = "kubernetes.request.timeout";
    public static final String KUBERNETES_ROLLING_TIMEOUT_SYSTEM_PROPERTY = "kubernetes.rolling.timeout";
    public static final String KUBERNETES_LOGGING_INTERVAL_SYSTEM_PROPERTY = "kubernetes.logging.interval";
    public static final String KUBERNETES_SCALE_TIMEOUT_SYSTEM_PROPERTY = "kubernetes.scale.timeout";
    public static final String KUBERNETES_WEBSOCKET_TIMEOUT_SYSTEM_PROPERTY = "kubernetes.websocket.timeout";
    public static final String KUBERNETES_WEBSOCKET_PING_INTERVAL_SYSTEM_PROPERTY = "kubernetes.websocket.ping.interval";
    public static final String KUBERNETES_MAX_CONCURRENT_REQUESTS = "kubernetes.max.concurrent.requests";
    public static final String KUBERNETES_MAX_CONCURRENT_REQUESTS_PER_HOST = "kubernetes.max.concurrent.requests.per.host";

    public static final String KUBERNETES_IMPERSONATE_USERNAME = "kubernetes.impersonate.username";
    public static final String KUBERNETES_IMPERSONATE_GROUP = "kubernetes.impersonate.group";


    public static final String KUBERNETES_TRUSTSTORE_PASSPHRASE_PROPERTY = "kubernetes.truststore.passphrase";
    public static final String KUBERNETES_TRUSTSTORE_FILE_PROPERTY = "kubernetes.truststore.file";
    public static final String KUBERNETES_KEYSTORE_PASSPHRASE_PROPERTY = "kubernetes.keystore.passphrase";
    public static final String KUBERNETES_KEYSTORE_FILE_PROPERTY = "kubernetes.keystore.file";

    public static final String KUBERNETES_TLS_VERSIONS = "kubernetes.tls.versions";

    public static final String KUBERNETES_TRYNAMESPACE_PATH_SYSTEM_PROPERTY = "kubernetes.tryNamespacePath";
    public static final String KUBERNETES_NAMESPACE_PATH = "/var/run/secrets/kubernetes.io/serviceaccount/namespace";
    public static final String KUBERNETES_NAMESPACE_FILE = "kubenamespace";
    public static final String KUBERNETES_NAMESPACE_SYSTEM_PROPERTY = "kubernetes.namespace";
    public static final String KUBERNETES_KUBECONFIG_FILE = "kubeconfig";
    public static final String KUBERNETES_SERVICE_HOST_PROPERTY = "KUBERNETES_SERVICE_HOST";
    public static final String KUBERNETES_SERVICE_PORT_PROPERTY = "KUBERNETES_SERVICE_PORT";
    public static final String KUBERNETES_SERVICE_ACCOUNT_TOKEN_PATH = "/var/run/secrets/kubernetes.io/serviceaccount/token";
    public static final String KUBERNETES_SERVICE_ACCOUNT_CA_CRT_PATH = "/var/run/secrets/kubernetes.io/serviceaccount/ca.crt";
    public static final String KUBERNETES_HTTP2_DISABLE = "http2.disable";
    public static final String KUBERNETES_HTTP_PROXY = "http.proxy";
    public static final String KUBERNETES_HTTPS_PROXY = "https.proxy";
    public static final String KUBERNETES_ALL_PROXY = "all.proxy";
    public static final String KUBERNETES_NO_PROXY = "no.proxy";
    public static final String KUBERNETES_PROXY_USERNAME = "proxy.username";
    public static final String KUBERNETES_PROXY_PASSWORD = "proxy.password";

    public static final String KUBERNETES_USER_AGENT = "fabric8-kubernetes-client/" + Version.clientVersion();

    public static final String DEFAULT_MASTER_URL = "https://kubernetes.default.svc";
    public static final Long DEFAULT_ROLLING_TIMEOUT = 15 * 60 * 1000L;
    public static final Long DEFAULT_SCALE_TIMEOUT = 10 * 60 * 1000L;
    public static final int DEFAULT_LOGGING_INTERVAL = 20 * 1000;
    public static final Long DEFAULT_WEBSOCKET_TIMEOUT = 5 * 1000L;
    public static final Long DEFAULT_WEBSOCKET_PING_INTERVAL = 30 * 1000L;

    public static final Integer DEFAULT_MAX_CONCURRENT_REQUESTS = 64;
    public static final Integer DEFAULT_MAX_CONCURRENT_REQUESTS_PER_HOST = 5;

    public static final String HTTP_PROTOCOL_PREFIX = "http://";
    public static final String HTTPS_PROTOCOL_PREFIX = "https://";

    private static final String ACCESS_TOKEN = "access-token";
    private static final String ID_TOKEN = "id-token";


    private boolean trustCerts;
    private boolean disableHostnameVerification;
    private String masterUrl = DEFAULT_MASTER_URL;
    private String apiVersion = "v1";
    private String namespace;
    private String caCertFile;
    private String caCertData;

    private String clientCertFile;
    private String clientCertData;
    private String clientKeyFile;
    private String clientKeyData;
    private String clientKeyAlgo = "RSA";
    private String clientKeyPassphrase = "changeit";

    private String trustStoreFile;
    private String trustStorePassphrase;
    private String keyStoreFile;
    private String keyStorePassphrase;

    //context
    private List<NamedContext> contexts = new ArrayList<>();
    //current-context
    private NamedContext currentContext = null;

    private String username;
    private String password;
    private String oauthToken;
    private int watchReconnectInterval = 1000;
    private int watchReconnectLimit = -1;
    private int connectionTimeout = 10 * 1000;
    private int requestTimeout = 10 * 1000;
    private long rollingTimeout = DEFAULT_ROLLING_TIMEOUT;
    private long scaleTimeout = DEFAULT_SCALE_TIMEOUT;
    private int loggingInterval = DEFAULT_LOGGING_INTERVAL;
    private long websocketTimeout = DEFAULT_WEBSOCKET_TIMEOUT;
    private long websocketPingInterval = DEFAULT_WEBSOCKET_PING_INTERVAL;
    private int maxConcurrentRequests = DEFAULT_MAX_CONCURRENT_REQUESTS;
    private int maxConcurrentRequestsPerHost = DEFAULT_MAX_CONCURRENT_REQUESTS_PER_HOST;
    private String impersonateUsername;
    private OAuthTokenProvider oauthTokenProvider;

    @Deprecated
    private String impersonateGroup;
    private String[] impersonateGroups;
    private Map<String, List<String>> impersonateExtras;
    /**
     * end of fields not used but needed for builder generation.
     */

    private boolean http2Disable;
    private String httpProxy;
    private String httpsProxy;
    private String proxyUsername;
    private String proxyPassword;
    private String[] noProxy;
    private String userAgent;

    private TlsVersion[] tlsVersions = new TlsVersion[]{TLS_1_2};

    private Map<Integer, String> errorMessages = new HashMap<>();

    /**
     * custom headers
     */
    private Map<String, String> customHeaders = null;

    private Boolean autoConfigure = Boolean.FALSE;

    private File file;

    public Config() {
        this(!Utils.getSystemPropertyOrEnvVar(KUBERNETES_DISABLE_AUTO_CONFIG_SYSTEM_PROPERTY, false));
    }

    private Config(Boolean autoConfigure) {
        if (Boolean.TRUE.equals(autoConfigure)) {
            this.autoConfigure = Boolean.TRUE;
            autoConfigure(this, null);
        }
    }

    public static Config empty() {

        return new Config(false);
    }

    public static Config autoConfigure(String context) {
        Config config = new Config();
        return autoConfigure(config, context);
    }

    private static Config autoConfigure(Config config, String context) {
//        if (!tryKubeConfig(config, context)) {
//            tryServiceAccount(config);
//            tryNamespaceFromPath(config);
//        }
//        configFromSysPropsOrEnvVars(config);
//
//        config.masterUrl = ensureHttps(config.masterUrl, config);
//        config.masterUrl = ensureEndsWithSlash(config.masterUrl);

        return config;
    }


    public static Config fromKubeconfig(String kubeconfigContents) throws IOException {
        return fromKubeconfig(null, kubeconfigContents, null);
    }

    // Note: kubeconfigPath is optional (see note on loadFromKubeConfig)
    public static Config fromKubeconfig(String context, String kubeconfigContents, String kubeconfigPath) {
        // we allow passing context along here, since downstream accepts it
        Config config = new Config();
        if (kubeconfigPath != null)
            config.file = new File(kubeconfigPath);
        loadFromKubeconfig(config, context, kubeconfigContents);
        return config;
    }

    /**
     * 从 kubeconfig 读取配置
     *
     * @param config
     * @param context
     * @param kubeconfigContents
     * @return
     */
    private static boolean loadFromKubeconfig(Config config, String context, String kubeconfigContents) {
        try {
            com.kubernetes.api.model.Config kubeConfig = KubeConfigUtils.parseConfigFromString(kubeconfigContents);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
