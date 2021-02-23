package com.k8s.test;

import com.kubernetes.api.model.Config;
import com.kubernetes.client.internal.KubeConfigUtils;
import com.kubernetes.client.util.IOHelpers;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KubeConfigUtilTest {

    @Test
    public void test1() throws IOException {
        InputStream is = getClass().getResourceAsStream("/kube_config.yaml");
        String content = IOHelpers.readFully(is);

        Config config = KubeConfigUtils.parseConfigFromString(content);

        log.info("{}", config);
    }
}
