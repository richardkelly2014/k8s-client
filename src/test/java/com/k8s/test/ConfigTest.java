package com.k8s.test;

import com.kubernetes.client.Config;
import com.kubernetes.client.internal.CertUtils;
import com.kubernetes.client.util.IOHelpers;

import com.kubernetes.client.util.Utils;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;

import lombok.extern.slf4j.Slf4j;

import static com.kubernetes.client.internal.SSLUtils.keyManagers;
import static com.kubernetes.client.internal.SSLUtils.trustManagers;

@Slf4j
public class ConfigTest {

    @Test
    public void configLoadKubeConfig() throws IOException, CertificateException, NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException, InvalidKeySpecException {
        InputStream is = getClass().getResourceAsStream("/kube_config.yaml");
        String content = IOHelpers.readFully(is);

        Config config = Config.fromKubeconfig(content);

        log.info("{}", config);

        // test2(config);

        log.info("{}", trustManagers(config));

        log.info("{}", keyManagers(config));
    }

    private void test2(Config config) throws IOException {
        String data = config.getClientKeyData();
        String file = config.getClientKeyFile();
        InputStream inputStream = CertUtils.getInputStreamFromDataOrFile(data, file);

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line, algorithm = null;

            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains("BEGIN EC PRIVATE KEY")) {
                    algorithm = "EC";
                    break;
                } else if (line.contains("BEGIN RSA PRIVATE KEY")) {
                    algorithm = "RSA";
                    break;
                }
            }
            log.info("{}", algorithm);

        }
    }

}
