package com.k8s.test;

import com.kubernetes.client.Config;
import com.kubernetes.client.util.HttpClientUtils;
import com.kubernetes.client.util.IOHelpers;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Slf4j
public class HttpClientTest {

    @Test
    public void test1() throws IOException {
        InputStream is = getClass().getResourceAsStream("/kube_config.yaml");
        String content = IOHelpers.readFully(is);

        Config config = Config.fromKubeconfig(content);

        OkHttpClient httpClient = HttpClientUtils.createHttpClient(config);

        String url = config.getMasterUrl();

        Request request = new Request.Builder().get().url(url).build();

        Response response = httpClient.newCall(request).execute();

        log.info("{}", response);
    }
}
