package com.kubernetes.client.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static com.kubernetes.client.util.Utils.interpolateString;

public class ReplaceValueStream {

    private final Map<String, String> valuesMap;

    public static InputStream replaceValues(InputStream is, Map<String, String> valuesMap) throws IOException {
        return new ReplaceValueStream(valuesMap).createInputStream(is);
    }

    private ReplaceValueStream(Map<String, String> valuesMap) {
        this.valuesMap = valuesMap;
    }

    private InputStream createInputStream(InputStream is) throws IOException {
        return new ByteArrayInputStream(
                interpolateString(IOHelpers.readFully(is), valuesMap).getBytes(StandardCharsets.UTF_8));
    }
}
