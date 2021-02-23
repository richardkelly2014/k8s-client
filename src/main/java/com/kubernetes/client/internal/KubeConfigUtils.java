package com.kubernetes.client.internal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kubernetes.api.model.AuthInfo;
import com.kubernetes.api.model.Cluster;
import com.kubernetes.api.model.Config;
import com.kubernetes.api.model.Context;
import com.kubernetes.api.model.NamedAuthInfo;
import com.kubernetes.api.model.NamedCluster;
import com.kubernetes.api.model.NamedContext;
import com.kubernetes.client.util.Serialization;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class KubeConfigUtils {

    private KubeConfigUtils() {
    }

    public static Config parseConfig(File file) throws IOException {
        ObjectMapper mapper = Serialization.yamlMapper();
        return mapper.readValue(file, Config.class);
    }

    public static Config parseConfigFromString(String contents) throws IOException {
        ObjectMapper mapper = Serialization.yamlMapper();
        return mapper.readValue(contents, Config.class);
    }

    public static NamedContext getCurrentContext(Config config) {
        String contextName = config.getCurrentContext();
        if (contextName != null) {
            List<NamedContext> contexts = config.getContexts();
            if (contexts != null) {
                for (NamedContext context : contexts) {
                    if (contextName.equals(context.getName())) {
                        return context;
                    }
                }
            }
        }
        return null;
    }

    public static String getUserToken(Config config, Context context) {
        AuthInfo authInfo = getUserAuthInfo(config, context);
        if (authInfo != null) {
            return authInfo.getToken();
        }
        return null;
    }

    public static AuthInfo getUserAuthInfo(Config config, Context context) {
        AuthInfo authInfo = null;
        if (config != null && context != null) {
            String user = context.getUser();
            if (user != null) {
                List<NamedAuthInfo> users = config.getUsers();
                if (users != null) {
                    authInfo = users.stream()
                            .filter(u -> u.getName().equals(user))
                            .findAny()
                            .map(NamedAuthInfo::getUser)
                            .orElse(null);
                }
            }
        }
        return authInfo;
    }

    public static Cluster getCluster(Config config, Context context) {
        Cluster cluster = null;
        if (config != null && context != null) {
            String clusterName = context.getCluster();
            if (clusterName != null) {
                List<NamedCluster> clusters = config.getClusters();
                if (clusters != null) {
                    cluster = clusters.stream()
                            .filter(c -> c.getName().equals(clusterName))
                            .findAny()
                            .map(NamedCluster::getCluster)
                            .orElse(null);
                }
            }
        }
        return cluster;
    }

    public static int getNamedUserIndexFromConfig(Config config, String userName) {
        for (int i = 0; i < config.getUsers().size(); i++) {
            if (config.getUsers().get(i).getName().equals(userName)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 写入文件
     *
     * @param kubeConfig
     * @param kubeConfigPath
     * @throws IOException
     */
    public static void persistKubeConfigIntoFile(Config kubeConfig, String kubeConfigPath) throws IOException {
        Serialization.yamlMapper().writeValue(new File(kubeConfigPath), kubeConfig);
    }
}
