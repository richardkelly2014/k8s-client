package com.k8s.test;

import com.kubernetes.client.Config;
import com.kubernetes.client.util.IOHelpers;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConfigTest {

    @Test
    public void configLoadKubeConfig() throws IOException {
        InputStream is = getClass().getResourceAsStream("/kube_config.yaml");
        String content = IOHelpers.readFully(is);

        Config config = Config.fromKubeconfig(content);

        log.info("{}", config);
    }
}
