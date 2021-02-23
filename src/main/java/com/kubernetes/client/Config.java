package com.kubernetes.client;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kubernetes.api.model.AuthInfo;
import com.kubernetes.api.model.Cluster;
import com.kubernetes.api.model.Context;
import com.kubernetes.api.model.ExecConfig;
import com.kubernetes.api.model.ExecEnvVar;
import com.kubernetes.api.model.NamedContext;
import com.kubernetes.client.internal.CertUtils;
import com.kubernetes.client.internal.KubeConfigUtils;
import com.kubernetes.client.internal.SSLUtils;
import com.kubernetes.client.util.IOHelpers;
import com.kubernetes.client.util.Serialization;
import com.kubernetes.client.util.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import lombok.ToString;
import okhttp3.TlsVersion;

import static okhttp3.TlsVersion.TLS_1_2;

@ToString
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
    // kubeConfig cluster
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

    private RequestConfig requestConfig = new RequestConfig();

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
        //this(!Utils.getSystemPropertyOrEnvVar(KUBERNETES_DISABLE_AUTO_CONFIG_SYSTEM_PROPERTY, false));
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
        //优先尝试 kubeconfig
        if (!tryKubeConfig(config, context)) {
            //service account /var/run/secerts/kubernets.io/
            //在容器内
            tryServiceAccount(config);
            tryNamespaceFromPath(config);
        }
        configFromSysPropsOrEnvVars(config);

        //config.masterUrl = ensureHttps(config.masterUrl, config);
        config.masterUrl = ensureEndsWithSlash(config.masterUrl);

        return config;
    }

    private static String ensureEndsWithSlash(String masterUrl) {
        if (!masterUrl.endsWith("/")) {
            masterUrl = masterUrl + "/";
        }
        return masterUrl;
    }


    public static void configFromSysPropsOrEnvVars(Config config) {
        config.setTrustCerts(Utils.getSystemPropertyOrEnvVar(KUBERNETES_TRUST_CERT_SYSTEM_PROPERTY, config.isTrustCerts()));
        config.setDisableHostnameVerification(Utils.getSystemPropertyOrEnvVar(KUBERNETES_DISABLE_HOSTNAME_VERIFICATION_SYSTEM_PROPERTY, config.isDisableHostnameVerification()));

        config.setMasterUrl(Utils.getSystemPropertyOrEnvVar(KUBERNETES_MASTER_SYSTEM_PROPERTY, config.getMasterUrl()));
        config.setApiVersion(Utils.getSystemPropertyOrEnvVar(KUBERNETES_API_VERSION_SYSTEM_PROPERTY, config.getApiVersion()));
        config.setNamespace(Utils.getSystemPropertyOrEnvVar(KUBERNETES_NAMESPACE_SYSTEM_PROPERTY, config.getNamespace()));

        config.setCaCertFile(Utils.getSystemPropertyOrEnvVar(KUBERNETES_CA_CERTIFICATE_FILE_SYSTEM_PROPERTY, config.getCaCertFile()));
        config.setCaCertData(Utils.getSystemPropertyOrEnvVar(KUBERNETES_CA_CERTIFICATE_DATA_SYSTEM_PROPERTY, config.getCaCertData()));

        config.setClientCertFile(Utils.getSystemPropertyOrEnvVar(KUBERNETES_CLIENT_CERTIFICATE_FILE_SYSTEM_PROPERTY, config.getClientCertFile()));
        config.setClientCertData(Utils.getSystemPropertyOrEnvVar(KUBERNETES_CLIENT_CERTIFICATE_DATA_SYSTEM_PROPERTY, config.getClientCertData()));

        config.setClientKeyFile(Utils.getSystemPropertyOrEnvVar(KUBERNETES_CLIENT_KEY_FILE_SYSTEM_PROPERTY, config.getClientKeyFile()));
        config.setClientKeyData(Utils.getSystemPropertyOrEnvVar(KUBERNETES_CLIENT_KEY_DATA_SYSTEM_PROPERTY, config.getClientKeyData()));

        config.setClientKeyAlgo(getKeyAlgorithm(config.getClientKeyFile(), config.getClientKeyData()));
        config.setClientKeyPassphrase(Utils.getSystemPropertyOrEnvVar(KUBERNETES_CLIENT_KEY_PASSPHRASE_SYSTEM_PROPERTY, new String(config.getClientKeyPassphrase())));
        config.setUserAgent(Utils.getSystemPropertyOrEnvVar(KUBERNETES_USER_AGENT, config.getUserAgent()));

        config.setTrustStorePassphrase(Utils.getSystemPropertyOrEnvVar(KUBERNETES_TRUSTSTORE_PASSPHRASE_PROPERTY, config.getTrustStorePassphrase()));
        config.setTrustStoreFile(Utils.getSystemPropertyOrEnvVar(KUBERNETES_TRUSTSTORE_FILE_PROPERTY, config.getTrustStoreFile()));
        config.setKeyStorePassphrase(Utils.getSystemPropertyOrEnvVar(KUBERNETES_KEYSTORE_PASSPHRASE_PROPERTY, config.getKeyStorePassphrase()));
        config.setKeyStoreFile(Utils.getSystemPropertyOrEnvVar(KUBERNETES_KEYSTORE_FILE_PROPERTY, config.getKeyStoreFile()));

        config.setOauthToken(Utils.getSystemPropertyOrEnvVar(KUBERNETES_OAUTH_TOKEN_SYSTEM_PROPERTY, config.getOauthToken()));
        config.setUsername(Utils.getSystemPropertyOrEnvVar(KUBERNETES_AUTH_BASIC_USERNAME_SYSTEM_PROPERTY, config.getUsername()));
        config.setPassword(Utils.getSystemPropertyOrEnvVar(KUBERNETES_AUTH_BASIC_PASSWORD_SYSTEM_PROPERTY, config.getPassword()));

        config.setImpersonateUsername(Utils.getSystemPropertyOrEnvVar(KUBERNETES_IMPERSONATE_USERNAME, config.getImpersonateUsername()));

        String configuredImpersonateGroups = Utils.getSystemPropertyOrEnvVar(KUBERNETES_IMPERSONATE_GROUP, config.getImpersonateGroup());
        if (configuredImpersonateGroups != null) {
            config.setImpersonateGroups(configuredImpersonateGroups.split(","));
        }

        String configuredWatchReconnectInterval = Utils.getSystemPropertyOrEnvVar(KUBERNETES_WATCH_RECONNECT_INTERVAL_SYSTEM_PROPERTY);
        if (configuredWatchReconnectInterval != null) {
            config.setWatchReconnectInterval(Integer.parseInt(configuredWatchReconnectInterval));
        }

        String configuredWatchReconnectLimit = Utils.getSystemPropertyOrEnvVar(KUBERNETES_WATCH_RECONNECT_LIMIT_SYSTEM_PROPERTY);
        if (configuredWatchReconnectLimit != null) {
            config.setWatchReconnectLimit(Integer.parseInt(configuredWatchReconnectLimit));
        }

        String configuredRollingTimeout = Utils.getSystemPropertyOrEnvVar(KUBERNETES_ROLLING_TIMEOUT_SYSTEM_PROPERTY, String.valueOf(DEFAULT_ROLLING_TIMEOUT));
        if (configuredRollingTimeout != null) {
            config.setRollingTimeout(Long.parseLong(configuredRollingTimeout));
        }

        String configuredScaleTimeout = Utils.getSystemPropertyOrEnvVar(KUBERNETES_SCALE_TIMEOUT_SYSTEM_PROPERTY, String.valueOf(DEFAULT_SCALE_TIMEOUT));
        if (configuredScaleTimeout != null) {
            config.setScaleTimeout(Long.parseLong(configuredScaleTimeout));
        }


        String configuredLoggingInterval = Utils.getSystemPropertyOrEnvVar(KUBERNETES_LOGGING_INTERVAL_SYSTEM_PROPERTY, String.valueOf(DEFAULT_LOGGING_INTERVAL));
        if (configuredLoggingInterval != null) {
            config.setLoggingInterval(Integer.parseInt(configuredLoggingInterval));
        }

        config.setConnectionTimeout(Utils.getSystemPropertyOrEnvVar(KUBERNETES_CONNECTION_TIMEOUT_SYSTEM_PROPERTY, config.getConnectionTimeout()));
        config.setRequestTimeout(Utils.getSystemPropertyOrEnvVar(KUBERNETES_REQUEST_TIMEOUT_SYSTEM_PROPERTY, config.getRequestTimeout()));

        String configuredWebsocketTimeout = Utils.getSystemPropertyOrEnvVar(KUBERNETES_WEBSOCKET_TIMEOUT_SYSTEM_PROPERTY, String.valueOf(config.getWebsocketTimeout()));
        if (configuredWebsocketTimeout != null) {
            config.setWebsocketTimeout(Long.parseLong(configuredWebsocketTimeout));
        }

        String configuredWebsocketPingInterval = Utils.getSystemPropertyOrEnvVar(KUBERNETES_WEBSOCKET_PING_INTERVAL_SYSTEM_PROPERTY, String.valueOf(config.getWebsocketPingInterval()));
        if (configuredWebsocketPingInterval != null) {
            config.setWebsocketPingInterval(Long.parseLong(configuredWebsocketPingInterval));
        }

        String configuredMaxConcurrentRequests = Utils.getSystemPropertyOrEnvVar(KUBERNETES_MAX_CONCURRENT_REQUESTS, String.valueOf(config.getMaxConcurrentRequests()));
        if (configuredMaxConcurrentRequests != null) {
            config.setMaxConcurrentRequests(Integer.parseInt(configuredMaxConcurrentRequests));
        }

        String configuredMaxConcurrentReqeustsPerHost = Utils.getSystemPropertyOrEnvVar(KUBERNETES_MAX_CONCURRENT_REQUESTS_PER_HOST, String.valueOf(config.getMaxConcurrentRequestsPerHost()));
        if (configuredMaxConcurrentReqeustsPerHost != null) {
            config.setMaxConcurrentRequestsPerHost(Integer.parseInt(configuredMaxConcurrentReqeustsPerHost));
        }

        config.setHttp2Disable(Utils.getSystemPropertyOrEnvVar(KUBERNETES_HTTP2_DISABLE, config.isHttp2Disable()));

        config.setHttpProxy(Utils.getSystemPropertyOrEnvVar(KUBERNETES_ALL_PROXY, config.getHttpProxy()));
        config.setHttpsProxy(Utils.getSystemPropertyOrEnvVar(KUBERNETES_ALL_PROXY, config.getHttpsProxy()));

        config.setHttpsProxy(Utils.getSystemPropertyOrEnvVar(KUBERNETES_HTTPS_PROXY, config.getHttpsProxy()));
        config.setHttpProxy(Utils.getSystemPropertyOrEnvVar(KUBERNETES_HTTP_PROXY, config.getHttpProxy()));

        config.setProxyUsername(Utils.getSystemPropertyOrEnvVar(KUBERNETES_PROXY_USERNAME, config.getProxyUsername()));
        config.setProxyPassword(Utils.getSystemPropertyOrEnvVar(KUBERNETES_PROXY_PASSWORD, config.getProxyPassword()));

        String noProxyVar = Utils.getSystemPropertyOrEnvVar(KUBERNETES_NO_PROXY);
        if (noProxyVar != null) {
            config.setNoProxy(noProxyVar.split(","));
        }

        String tlsVersionsVar = Utils.getSystemPropertyOrEnvVar(KUBERNETES_TLS_VERSIONS);
        if (tlsVersionsVar != null && !tlsVersionsVar.isEmpty()) {
            String[] tlsVersionsSplit = tlsVersionsVar.split(",");
            TlsVersion[] tlsVersions = new TlsVersion[tlsVersionsSplit.length];
            for (int i = 0; i < tlsVersionsSplit.length; i++) {
                tlsVersions[i] = TlsVersion.forJavaName(tlsVersionsSplit[i]);
            }
            config.setTlsVersions(tlsVersions);
        }
    }

    private static boolean tryServiceAccount(Config config) {
        LOGGER.debug("Trying to configure client from service account...");
        String masterHost = Utils.getSystemPropertyOrEnvVar(KUBERNETES_SERVICE_HOST_PROPERTY, (String) null);
        String masterPort = Utils.getSystemPropertyOrEnvVar(KUBERNETES_SERVICE_PORT_PROPERTY, (String) null);
        if (masterHost != null && masterPort != null) {
            String hostPort = joinHostPort(masterHost, masterPort);
            LOGGER.debug("Found service account host and port: " + hostPort);
            config.setMasterUrl("https://" + hostPort);
        }
        if (Utils.getSystemPropertyOrEnvVar(KUBERNETES_AUTH_TRYSERVICEACCOUNT_SYSTEM_PROPERTY, true)) {
            //容器内 ca文件
            boolean serviceAccountCaCertExists = Files.isRegularFile(new File(KUBERNETES_SERVICE_ACCOUNT_CA_CRT_PATH).toPath());
            if (serviceAccountCaCertExists) {
                LOGGER.debug("Found service account ca cert at: [" + KUBERNETES_SERVICE_ACCOUNT_CA_CRT_PATH + "].");
                config.setCaCertFile(KUBERNETES_SERVICE_ACCOUNT_CA_CRT_PATH);
            } else {
                LOGGER.debug("Did not find service account ca cert at: [" + KUBERNETES_SERVICE_ACCOUNT_CA_CRT_PATH + "].");
            }
            try {
                //token
                String serviceTokenCandidate = new String(Files.readAllBytes(new File(KUBERNETES_SERVICE_ACCOUNT_TOKEN_PATH).toPath()));
                LOGGER.debug("Found service account token at: [" + KUBERNETES_SERVICE_ACCOUNT_TOKEN_PATH + "].");

                config.setOauthToken(serviceTokenCandidate);

                String txt = "Configured service account doesn't have access. Service account may have been revoked.";
                config.getErrorMessages().put(401, "Unauthorized! " + txt);
                config.getErrorMessages().put(403, "Forbidden!" + txt);
                return true;
            } catch (IOException e) {
                // No service account token available...
                LOGGER.warn("Error reading service account token from: [{}]. Ignoring.", KUBERNETES_SERVICE_ACCOUNT_TOKEN_PATH);
            }
        }
        return false;
    }

    private static boolean tryNamespaceFromPath(Config config) {
        LOGGER.debug("Trying to configure client namespace from Kubernetes service account namespace path...");
        if (Utils.getSystemPropertyOrEnvVar(KUBERNETES_TRYNAMESPACE_PATH_SYSTEM_PROPERTY, true)) {
            String serviceAccountNamespace = Utils.getSystemPropertyOrEnvVar(KUBERNETES_NAMESPACE_FILE, KUBERNETES_NAMESPACE_PATH);
            boolean serviceAccountNamespaceExists = Files.isRegularFile(new File(serviceAccountNamespace).toPath());
            if (serviceAccountNamespaceExists) {
                LOGGER.debug("Found service account namespace at: [" + serviceAccountNamespace + "].");
                try {
                    String namespace = new String(Files.readAllBytes(new File(serviceAccountNamespace).toPath()));
                    config.setNamespace(namespace.replace(System.lineSeparator(), ""));
                    return true;
                } catch (IOException e) {
                    LOGGER.error("Error reading service account namespace from: [" + serviceAccountNamespace + "].", e);
                }
            } else {
                LOGGER.debug("Did not find service account namespace at: [" + serviceAccountNamespace + "]. Ignoring.");
            }
        }
        return false;
    }

    private static String joinHostPort(String host, String port) {
        if (host.indexOf(':') >= 0) {
            // Host is an IPv6
            return "[" + host + "]:" + port;
        }
        return host + ":" + port;
    }

    //文件绝对路径
    private static String absolutify(File relativeTo, String filename) {
        if (filename == null) {
            return null;
        }

        File file = new File(filename);
        if (file.isAbsolute()) {
            return file.getAbsolutePath();
        }

        return new File(relativeTo.getParentFile(), filename).getAbsolutePath();
    }

    public static Config fromKubeconfig(String kubeconfigContents) throws IOException {
        return fromKubeconfig(null, kubeconfigContents, null);
    }

    public static Config fromKubeconfig(String context, String kubeconfigContents, String kubeconfigPath) {
        // we allow passing context along here, since downstream accepts it
        Config config = new Config();
        if (kubeconfigPath != null)
            config.file = new File(kubeconfigPath);
        loadFromKubeconfig(config, context, kubeconfigContents);
        return config;
    }

    /**
     * 尝试 .kube/config
     *
     * @param config
     * @param context
     * @return
     */
    private static boolean tryKubeConfig(Config config, String context) {
        LOGGER.debug("Trying to configure client from Kubernetes config...");
        if (!Utils.getSystemPropertyOrEnvVar(KUBERNETES_AUTH_TRYKUBECONFIG_SYSTEM_PROPERTY, true)) {
            return false;
        }
        File kubeConfigFile = new File(getKubeconfigFilename());
        if (!kubeConfigFile.isFile()) {
            LOGGER.debug("Did not find Kubernetes config at: [{}]. Ignoring.", kubeConfigFile.getPath());
            return false;
        }
        LOGGER.debug("Found for Kubernetes config at: [{}].", kubeConfigFile.getPath());
        String kubeconfigContents = getKubeconfigContents(kubeConfigFile);
        if (kubeconfigContents == null) {
            return false;
        }
        config.file = new File(kubeConfigFile.getPath());
        loadFromKubeconfig(config, context, kubeconfigContents);
        return true;
    }

    public static String getKubeconfigFilename() {
        String fileName = Utils.getSystemPropertyOrEnvVar(KUBERNETES_KUBECONFIG_FILE, new File(getHomeDir(), ".kube" + File.separator + "config").toString());

        // if system property/env var contains multiple files take the first one based on the environment
        // we are running in (eg. : for Linux, ; for Windows)
        String[] fileNames = fileName.split(File.pathSeparator);

        if (fileNames.length > 1) {
            LOGGER.warn("Found multiple Kubernetes config files [{}], using the first one: [{}]. If not desired file, please change it by doing `export KUBECONFIG=/path/to/kubeconfig` on Unix systems or `$Env:KUBECONFIG=/path/to/kubeconfig` on Windows.", fileNames, fileNames[0]);
            fileName = fileNames[0];
        }
        return fileName;
    }

    private static String getKubeconfigContents(File kubeConfigFile) {
        String kubeconfigContents = null;
        try (FileReader reader = new FileReader(kubeConfigFile)) {
            kubeconfigContents = IOHelpers.readFully(reader);
        } catch (IOException e) {
            LOGGER.error("Could not load Kubernetes config file from {}", kubeConfigFile.getPath(), e);
            return null;
        }
        return kubeconfigContents;
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

            config.setContexts(kubeConfig.getContexts());

            Context currentContext = setCurrentContext(context, config, kubeConfig);
            Cluster currentCluster = KubeConfigUtils.getCluster(kubeConfig, currentContext);

            if (currentContext != null) {
                config.setNamespace(currentContext.getNamespace());
            }

            if (currentCluster != null) {

                config.setMasterUrl(currentCluster.getServer());

                config.setTrustCerts(currentCluster.getInsecureSkipTlsVerify() != null && currentCluster.getInsecureSkipTlsVerify());
                config.setDisableHostnameVerification(currentCluster.getInsecureSkipTlsVerify() != null && currentCluster.getInsecureSkipTlsVerify());
                config.setCaCertData(currentCluster.getCertificateAuthorityData());
                //current-user
                AuthInfo currentAuthInfo = KubeConfigUtils.getUserAuthInfo(kubeConfig, currentContext);
                if (currentAuthInfo != null) {
                    // rewrite tls asset paths if needed
                    String caCertFile = currentCluster.getCertificateAuthority();
                    String clientCertFile = currentAuthInfo.getClientCertificate();
                    String clientKeyFile = currentAuthInfo.getClientKey();

                    File configFile = config.file;
                    if (configFile != null) {
                        caCertFile = absolutify(configFile, currentCluster.getCertificateAuthority());
                        clientCertFile = absolutify(configFile, currentAuthInfo.getClientCertificate());
                        clientKeyFile = absolutify(configFile, currentAuthInfo.getClientKey());
                    }

                    config.setCaCertFile(caCertFile);
                    config.setClientCertFile(clientCertFile);
                    //cert-data
                    config.setClientCertData(currentAuthInfo.getClientCertificateData());

                    //key-data
                    config.setClientKeyFile(clientKeyFile);
                    config.setClientKeyData(currentAuthInfo.getClientKeyData());

                    //token
                    config.setOauthToken(currentAuthInfo.getToken());

                    config.setUsername(currentAuthInfo.getUsername());
                    config.setPassword(currentAuthInfo.getPassword());

                    //token 为空
                    if (Utils.isNullOrEmpty(config.getOauthToken()) && currentAuthInfo.getAuthProvider() != null) {
                        if (currentAuthInfo.getAuthProvider().getConfig() != null) {
                            if (!Utils.isNullOrEmpty(currentAuthInfo.getAuthProvider().getConfig().get(ACCESS_TOKEN))) {
                                // GKE token
                                config.setOauthToken(currentAuthInfo.getAuthProvider().getConfig().get(ACCESS_TOKEN));
                            } else if (!Utils.isNullOrEmpty(currentAuthInfo.getAuthProvider().getConfig().get(ID_TOKEN))) {
                                // OpenID Connect token
                                config.setOauthToken(currentAuthInfo.getAuthProvider().getConfig().get(ID_TOKEN));
                            }
                        }
                    } else if (config.getOauthTokenProvider() == null) {  // https://kubernetes.io/docs/reference/access-authn-authz/authentication/#client-go-credential-plugins
                        ExecConfig exec = currentAuthInfo.getExec();
                        if (exec != null) {
                            ExecCredential ec = getExecCredentialFromExecConfig(exec, configFile);
                            if (ec != null && ec.status != null && ec.status.token != null) {
                                config.setOauthToken(ec.status.token);
                            } else {
                                LOGGER.warn("No token returned");
                            }
                        }
                    }

                    config.getErrorMessages().put(401, "Unauthorized! Token may have expired! Please log-in again.");
                    config.getErrorMessages().put(403, "Forbidden! User " + (currentContext != null ? currentContext.getUser() : "") + " doesn't have permission.");
                }
                return true;
            }

        } catch (IOException | InterruptedException e) {
            LOGGER.error("Failed to parse the kubeconfig.", e);
        }
        return false;
    }

    protected static ExecCredential getExecCredentialFromExecConfig(ExecConfig exec, File configFile) throws IOException, InterruptedException {
        String apiVersion = exec.getApiVersion();
        if ("client.authentication.k8s.io/v1alpha1".equals(apiVersion) || "client.authentication.k8s.io/v1beta1".equals(apiVersion)) {
            List<ExecEnvVar> env = exec.getEnv();
            // TODO check behavior of tty & stdin
            ProcessBuilder pb = new ProcessBuilder(getAuthenticatorCommandFromExecConfig(exec, configFile, Utils.getSystemPathVariable()));
            pb.redirectErrorStream(true);
            if (env != null) {
                Map<String, String> environment = pb.environment();
                env.forEach(var -> environment.put(var.getName(), var.getValue()));
            }
            Process p = pb.start();
            String output;
            try (InputStream is = p.getInputStream()) {
                output = IOHelpers.readFully(is);
            }
            if (p.waitFor() != 0) {
                LOGGER.warn(output);
            }
            ExecCredential ec = Serialization.unmarshal(output, ExecCredential.class);
            if (!apiVersion.equals(ec.apiVersion)) {
                LOGGER.warn("Wrong apiVersion {} vs. {}", ec.apiVersion, apiVersion);
            } else {
                return ec;
            }
        } else { // TODO v1beta1?
            LOGGER.warn("Unsupported apiVersion: {}", apiVersion);
        }
        return null;
    }

    protected static List<String> getAuthenticatorCommandFromExecConfig(ExecConfig exec, File configFile, String systemPathValue) {
        String command = exec.getCommand();
        if (command.contains(File.separator) && !command.startsWith(File.separator) && configFile != null) {
            // Appears to be a relative path; normalize. Spec is vague about how to detect this situation.
            command = Paths.get(configFile.getAbsolutePath()).resolveSibling(command).normalize().toString();
        }
        List<String> argv = new ArrayList<>(Utils.getCommandPlatformPrefix());
        command = getCommandWithFullyQualifiedPath(command, systemPathValue);
        List<String> args = exec.getArgs();
        if (args != null) {
            argv.add(command + " " + String.join(" ", args));
        }
        return argv;
    }

    protected static String getCommandWithFullyQualifiedPath(String command, String pathValue) {
        String[] pathParts = pathValue.split(File.pathSeparator);

        // Iterate through path in order to find executable file
        for (String pathPart : pathParts) {
            File commandFile = new File(pathPart + File.separator + command);
            if (commandFile.exists()) {
                return commandFile.getAbsolutePath();
            }
        }

        return command;
    }

    private static Context setCurrentContext(String context, Config config,
                                             com.kubernetes.api.model.Config kubeConfig) {
        if (context != null) {
            kubeConfig.setCurrentContext(context);
        }
        Context currentContext = null;
        NamedContext currentNamedContext = KubeConfigUtils.getCurrentContext(kubeConfig);
        if (currentNamedContext != null) {
            config.setCurrentContext(currentNamedContext);
            currentContext = currentNamedContext.getContext();
        }
        return currentContext;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static final class ExecCredential {
        public String kind;
        public String apiVersion;
        public ExecCredentialSpec spec;
        public ExecCredentialStatus status;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static final class ExecCredentialSpec {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static final class ExecCredentialStatus {
        public String token;
        // TODO clientCertificateData, clientKeyData, expirationTimestamp
    }

    private static String getHomeDir() {
        String osName = System.getProperty("os.name").toLowerCase(Locale.ROOT);
        if (osName.startsWith("win")) {
            String homeDrive = System.getenv("HOMEDRIVE");
            String homePath = System.getenv("HOMEPATH");
            if (homeDrive != null && !homeDrive.isEmpty() && homePath != null && !homePath.isEmpty()) {
                String homeDir = homeDrive + homePath;
                File f = new File(homeDir);
                if (f.exists() && f.isDirectory()) {
                    return homeDir;
                }
            }
            String userProfile = System.getenv("USERPROFILE");
            if (userProfile != null && !userProfile.isEmpty()) {
                File f = new File(userProfile);
                if (f.exists() && f.isDirectory()) {
                    return userProfile;
                }
            }
        }
        String home = System.getenv("HOME");
        if (home != null && !home.isEmpty()) {
            File f = new File(home);
            if (f.exists() && f.isDirectory()) {
                return home;
            }
        }

        //Fall back to user.home should never really get here
        return System.getProperty("user.home", ".");
    }

    public static String getKeyAlgorithm(InputStream inputStream) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line, algorithm = null;

            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains("BEGIN EC PRIVATE KEY"))
                    algorithm = "EC";
                else if (line.contains("BEGIN RSA PRIVATE KEY")) {
                    algorithm = "RSA";
                }
            }
            return algorithm;
        }
    }

    public static String getKeyAlgorithm(String clientKeyFile, String clientKeyData) {
        // Check if any system property is set
        if (Utils.getSystemPropertyOrEnvVar(KUBERNETES_CLIENT_KEY_ALGO_SYSTEM_PROPERTY) != null) {
            return Utils.getSystemPropertyOrEnvVar(KUBERNETES_CLIENT_KEY_ALGO_SYSTEM_PROPERTY);
        }

        // Detect algorithm
        try {
            InputStream keyInputStream = CertUtils.getInputStreamFromDataOrFile(clientKeyData, clientKeyFile);
            if (keyInputStream != null) {
                return getKeyAlgorithm(keyInputStream);
            }
        } catch (IOException exception) {
            LOGGER.debug("Failure in determining private key algorithm type, defaulting to RSA ", exception.getMessage());
        }
        return null;
    }

    @JsonProperty("oauthToken")
    public String getOauthToken() {
        return getRequestConfig().getOauthToken();
    }

    public void setOauthToken(String oauthToken) {
        this.requestConfig.setOauthToken(oauthToken);
    }

    @JsonProperty("password")
    public String getPassword() {
        return getRequestConfig().getPassword();
    }

    public void setPassword(String password) {
        this.requestConfig.setPassword(password);
    }

    @JsonProperty("username")
    public String getUsername() {
        return getRequestConfig().getUsername();
    }

    public void setUsername(String username) {
        this.requestConfig.setUsername(username);
    }

    @JsonProperty("impersonateUsername")
    public String getImpersonateUsername() {
        return getRequestConfig().getImpersonateUsername();
    }

    public void setImpersonateUsername(String impersonateUsername) {
        this.requestConfig.setImpersonateUsername(impersonateUsername);
    }

    @JsonProperty("impersonateGroups")
    public String[] getImpersonateGroups() {
        return getRequestConfig().getImpersonateGroups();
    }

    public void setImpersonateGroups(String... impersonateGroup) {
        this.requestConfig.setImpersonateGroups(impersonateGroup);
    }

    /**
     * @return returns string of impersonate group
     * @deprecated Use {@link #getImpersonateGroups()} instead
     */
    @Deprecated
    @JsonProperty("impersonateGroup")
    public String getImpersonateGroup() {
        return getRequestConfig().getImpersonateGroup();
    }

    /**
     * @param impersonateGroup ImpersonateGroup string
     * @deprecated Use {@link #setImpersonateGroups(String...)} instead
     */
    @Deprecated
    public void setImpersonateGroup(String impersonateGroup) {
        this.requestConfig.setImpersonateGroups(impersonateGroup);
    }

    @JsonProperty("impersonateExtras")
    public Map<String, List<String>> getImpersonateExtras() {
        return getRequestConfig().getImpersonateExtras();
    }

    public void setImpersonateExtras(Map<String, List<String>> impersonateExtras) {
        this.requestConfig.setImpersonateExtras(impersonateExtras);
    }

    @JsonProperty("clientKeyPassphrase")
    public String getClientKeyPassphrase() {
        return clientKeyPassphrase;
    }

    public void setClientKeyPassphrase(String clientKeyPassphrase) {
        this.clientKeyPassphrase = clientKeyPassphrase;
    }

    @JsonProperty("clientKeyAlgo")
    public String getClientKeyAlgo() {
        return clientKeyAlgo;
    }

    public void setClientKeyAlgo(String clientKeyAlgo) {
        this.clientKeyAlgo = clientKeyAlgo;
    }

    @JsonProperty("clientKeyData")
    public String getClientKeyData() {
        return clientKeyData;
    }

    public void setClientKeyData(String clientKeyData) {
        this.clientKeyData = clientKeyData;
    }

    @JsonProperty("clientKeyFile")
    public String getClientKeyFile() {
        return clientKeyFile;
    }

    public void setClientKeyFile(String clientKeyFile) {
        this.clientKeyFile = clientKeyFile;
    }

    @JsonProperty("clientCertData")
    public String getClientCertData() {
        return clientCertData;
    }

    public void setClientCertData(String clientCertData) {
        this.clientCertData = clientCertData;
    }

    @JsonProperty("clientCertFile")
    public String getClientCertFile() {
        return clientCertFile;
    }

    public void setClientCertFile(String clientCertFile) {
        this.clientCertFile = clientCertFile;
    }

    @JsonProperty("caCertData")
    public String getCaCertData() {
        return caCertData;
    }

    public void setCaCertData(String caCertData) {
        this.caCertData = caCertData;
    }

    @JsonProperty("caCertFile")
    public String getCaCertFile() {
        return caCertFile;
    }

    public void setCaCertFile(String caCertFile) {
        this.caCertFile = caCertFile;
    }

    @JsonProperty("apiVersion")
    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    @JsonProperty("masterUrl")
    public String getMasterUrl() {
        return masterUrl;
    }

    public void setMasterUrl(String masterUrl) {
        this.masterUrl = masterUrl;
    }

    @JsonProperty("trustCerts")
    public boolean isTrustCerts() {
        return trustCerts;
    }

    public void setTrustCerts(boolean trustCerts) {
        this.trustCerts = trustCerts;
    }

    @JsonProperty("disableHostnameVerification")
    public boolean isDisableHostnameVerification() {
        return disableHostnameVerification;
    }

    public void setDisableHostnameVerification(boolean disableHostnameVerification) {
        this.disableHostnameVerification = disableHostnameVerification;
    }

    @JsonProperty("watchReconnectInterval")
    public int getWatchReconnectInterval() {
        return requestConfig.getWatchReconnectInterval();
    }

    public void setWatchReconnectInterval(int watchReconnectInterval) {
        this.requestConfig.setWatchReconnectInterval(watchReconnectInterval);
    }

    @JsonProperty("watchReconnectLimit")
    public int getWatchReconnectLimit() {
        return getRequestConfig().getWatchReconnectLimit();
    }

    public void setWatchReconnectLimit(int watchReconnectLimit) {
        this.requestConfig.setWatchReconnectLimit(watchReconnectLimit);
    }

    @JsonProperty("errorMessages")
    public Map<Integer, String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(Map<Integer, String> errorMessages) {
        this.errorMessages = errorMessages;
    }


    @JsonProperty("connectionTimeout")
    public int getConnectionTimeout() {
        return getRequestConfig().getConnectionTimeout();
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.requestConfig.setConnectionTimeout(connectionTimeout);
    }

    @JsonProperty("requestTimeout")
    public int getRequestTimeout() {
        return getRequestConfig().getRequestTimeout();
    }

    public void setRequestTimeout(int requestTimeout) {
        this.requestConfig.setRequestTimeout(requestTimeout);
    }

    @JsonProperty("rollingTimeout")
    public long getRollingTimeout() {
        return getRequestConfig().getRollingTimeout();
    }

    public void setRollingTimeout(long rollingTimeout) {
        this.requestConfig.setRollingTimeout(rollingTimeout);
    }

    @JsonProperty("scaleTimeout")
    public long getScaleTimeout() {
        return getRequestConfig().getScaleTimeout();
    }

    public void setScaleTimeout(long scaleTimeout) {
        this.requestConfig.setScaleTimeout(scaleTimeout);
    }

    @JsonProperty("loggingInterval")
    public int getLoggingInterval() {
        return getRequestConfig().getLoggingInterval();
    }

    public void setLoggingInterval(int loggingInterval) {
        this.requestConfig.setLoggingInterval(loggingInterval);
    }

    @JsonProperty("http2Disable")
    public boolean isHttp2Disable() {
        return http2Disable;
    }

    public void setHttp2Disable(boolean http2Disable) {
        this.http2Disable = http2Disable;
    }

    public void setHttpProxy(String httpProxy) {
        this.httpProxy = httpProxy;
    }

    @JsonProperty("httpProxy")
    public String getHttpProxy() {
        return httpProxy;
    }

    public void setHttpsProxy(String httpsProxy) {
        this.httpsProxy = httpsProxy;
    }

    @JsonProperty("httpsProxy")
    public String getHttpsProxy() {
        return httpsProxy;
    }

    public void setNoProxy(String[] noProxy) {
        this.noProxy = noProxy;
    }

    @JsonProperty("noProxy")
    public String[] getNoProxy() {
        return noProxy;
    }

    @JsonProperty("userAgent")
    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    @JsonProperty("tlsVersions")
    public TlsVersion[] getTlsVersions() {
        return tlsVersions;
    }

    public void setTlsVersions(TlsVersion[] tlsVersions) {
        this.tlsVersions = tlsVersions;
    }

    @JsonProperty("websocketTimeout")
    public long getWebsocketTimeout() {
        return getRequestConfig().getWebsocketTimeout();
    }

    public void setWebsocketTimeout(long websocketTimeout) {
        this.requestConfig.setWebsocketTimeout(websocketTimeout);
    }

    @JsonProperty("websocketPingInterval")
    public long getWebsocketPingInterval() {
        return getRequestConfig().getWebsocketPingInterval();
    }

    public void setWebsocketPingInterval(long websocketPingInterval) {
        this.requestConfig.setWebsocketPingInterval(websocketPingInterval);
    }

    public int getMaxConcurrentRequests() {
        return getRequestConfig().getMaxConcurrentRequests();
    }

    public void setMaxConcurrentRequests(int maxConcurrentRequests) {
        this.requestConfig.setMaxConcurrentRequests(maxConcurrentRequests);
    }

    public int getMaxConcurrentRequestsPerHost() {
        return getRequestConfig().getMaxConcurrentRequestsPerHost();
    }

    public void setMaxConcurrentRequestsPerHost(int maxConcurrentRequestsPerHost) {
        this.requestConfig.setMaxConcurrentRequestsPerHost(maxConcurrentRequestsPerHost);
    }

    @JsonProperty("proxyUsername")
    public String getProxyUsername() {
        return proxyUsername;
    }

    public void setProxyUsername(String proxyUsername) {
        this.proxyUsername = proxyUsername;
    }

    @JsonProperty("proxyPassword")
    public String getProxyPassword() {
        return proxyPassword;
    }

    public void setProxyPassword(String proxyPassword) {
        this.proxyPassword = proxyPassword;
    }


    public RequestConfig getRequestConfig() {
        RequestConfig rc = RequestConfigHolder.get();
        return rc != null ? rc : this.requestConfig;
    }

    public void setTrustStorePassphrase(String trustStorePassphrase) {
        this.trustStorePassphrase = trustStorePassphrase;
    }

    @JsonProperty("trustStorePassphrase")
    public String getTrustStorePassphrase() {
        return trustStorePassphrase;
    }

    public void setKeyStorePassphrase(String keyStorePassphrase) {
        this.keyStorePassphrase = keyStorePassphrase;
    }

    @JsonProperty("keyStorePassphrase")
    public String getKeyStorePassphrase() {
        return keyStorePassphrase;
    }

    public void setTrustStoreFile(String trustStoreFile) {
        this.trustStoreFile = trustStoreFile;
    }

    @JsonProperty("trustStoreFile")
    public String getTrustStoreFile() {
        return trustStoreFile;
    }

    public void setKeyStoreFile(String keyStoreFile) {
        this.keyStoreFile = keyStoreFile;
    }

    @JsonProperty("keyStoreFile")
    public String getKeyStoreFile() {
        return keyStoreFile;
    }

    @JsonIgnore
    public OAuthTokenProvider getOauthTokenProvider() {
        return oauthTokenProvider;
    }

    public void setOauthTokenProvider(OAuthTokenProvider oauthTokenProvider) {
        this.oauthTokenProvider = oauthTokenProvider;
    }

    @JsonProperty("customHeaders")
    public Map<String, String> getCustomHeaders() {
        return customHeaders;
    }

    public void setCustomHeaders(Map<String, String> customHeaders) {
        this.customHeaders = customHeaders;
    }

    public Boolean getAutoConfigure() {
        return autoConfigure;
    }

    @JsonProperty("namespace")
    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }


    public List<NamedContext> getContexts() {
        return contexts;
    }

    public void setContexts(List<NamedContext> contexts) {
        this.contexts = contexts;
    }

    public NamedContext getCurrentContext() {
        return currentContext;
    }

    public void setCurrentContext(NamedContext context) {
        this.currentContext = context;
    }

    public File getFile() {
        return file;
    }
}
