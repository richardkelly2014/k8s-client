package com.kubernetes.client.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kubernetes.api.model.KubernetesResource;
import com.kubernetes.client.KubernetesClientException;

import org.yaml.snakeyaml.Yaml;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 序列化 （json与yaml)
 */
public class Serialization {

    //禁止实例
    private Serialization() {
    }

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    static {
        JSON_MAPPER.registerModule(new JavaTimeModule());
    }

    private static final ObjectMapper YAML_MAPPER = new ObjectMapper(new YAMLFactory());
    private static final String DOCUMENT_DELIMITER = "---";

    public static ObjectMapper jsonMapper() {

        return JSON_MAPPER;
    }

    public static ObjectMapper yamlMapper() {

        return YAML_MAPPER;
    }


    public static <T> String asJson(T object) {
        try {
            return JSON_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw KubernetesClientException.launderThrowable(e);
        }
    }

    public static <T> String asYaml(T object) {
        try {
            return YAML_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw KubernetesClientException.launderThrowable(e);
        }
    }

    public static <T> T unmarshal(InputStream is) {

        return unmarshal(is, JSON_MAPPER);
    }

    public static <T> T unmarshal(InputStream is, Map<String, String> parameters) {
        String specFile = readSpecFileFromInputStream(is);
        if (containsMultipleDocuments(specFile)) {
            return (T) getKubernetesResourceList(parameters, specFile);
        }
        return unmarshal(new ByteArrayInputStream(specFile.getBytes()), JSON_MAPPER, parameters);
    }

    public static <T> T unmarshal(InputStream is, ObjectMapper mapper) {
        return unmarshal(is, mapper, Collections.emptyMap());
    }

    public static <T> T unmarshal(InputStream is, ObjectMapper mapper, Map<String, String> parameters) {
        try (
                InputStream wrapped = parameters != null && !parameters.isEmpty() ? ReplaceValueStream.replaceValues(is, parameters) : is;
                BufferedInputStream bis = new BufferedInputStream(wrapped)
        ) {
            bis.mark(-1);
            int intch;
            do {
                intch = bis.read();
            } while (intch > -1 && Character.isWhitespace(intch));
            bis.reset();

            if (intch != '{') {
                return unmarshalYaml(bis, null);
            }
            return mapper.readerFor(KubernetesResource.class).readValue(bis);
        } catch (IOException e) {
            throw KubernetesClientException.launderThrowable(e);
        }
    }

    public static <T> T unmarshal(String str, final Class<T> type) {
        return unmarshal(str, type, Collections.emptyMap());
    }

    public static <T> T unmarshal(String str, final Class<T> type, Map<String, String> parameters) {
        try (InputStream is = new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8))) {
            return unmarshal(is, new TypeReference<T>() {
                @Override
                public Type getType() {
                    return type;
                }
            }, parameters);
        } catch (IOException e) {
            throw KubernetesClientException.launderThrowable(e);
        }
    }

    public static <T> T unmarshal(InputStream is, final Class<T> type) {
        return unmarshal(is, type, Collections.emptyMap());
    }

    public static <T> T unmarshal(InputStream is, final Class<T> type, Map<String, String> parameters) {
        return unmarshal(is, new TypeReference<T>() {
            @Override
            public Type getType() {
                return type;
            }
        }, parameters);
    }

    public static <T> T unmarshal(InputStream is, TypeReference<T> type) {
        return unmarshal(is, type, Collections.emptyMap());
    }

    public static <T> T unmarshal(InputStream is, TypeReference<T> type, Map<String, String> parameters) {
        try (
                InputStream wrapped = parameters != null && !parameters.isEmpty() ? ReplaceValueStream.replaceValues(is, parameters) : is;
                BufferedInputStream bis = new BufferedInputStream(wrapped)
        ) {
            bis.mark(-1);
            int intch;
            do {
                intch = bis.read();
            } while (intch > -1 && Character.isWhitespace(intch));
            bis.reset();

            ObjectMapper mapper = JSON_MAPPER;
            if (intch != '{') {
                return unmarshalYaml(bis, type);
            }
            return mapper.readValue(bis, type);
        } catch (IOException e) {
            throw KubernetesClientException.launderThrowable(e);
        }
    }

    private static <T> T unmarshalYaml(InputStream is, TypeReference<T> type) throws JsonProcessingException {
        final Yaml yaml = new Yaml();
        Map<String, Object> obj = yaml.load(is);
        String objAsJsonStr = JSON_MAPPER.writeValueAsString(obj);
        return unmarshalJsonStr(objAsJsonStr, type);
    }

    private static <T> T unmarshalJsonStr(String jsonString, TypeReference<T> type) throws JsonProcessingException {
        if (type != null) {
            return JSON_MAPPER.readValue(jsonString, type);
        }
        return JSON_MAPPER.readerFor(KubernetesResource.class).readValue(jsonString);
    }

    static boolean containsMultipleDocuments(String specFile) {
        final long validDocumentCount = splitSpecFile(specFile).stream().filter(Serialization::validate)
                .count();
        return validDocumentCount > 1;
    }

    private static List<KubernetesResource> getKubernetesResourceList(Map<String, String> parameters, String specFile) {
        return splitSpecFile(specFile).stream().filter(Serialization::validate)
                .map(document ->
                        (KubernetesResource) Serialization.unmarshal(new ByteArrayInputStream(document.getBytes()), parameters))
                .collect(Collectors.toList());
    }

    private static List<String> splitSpecFile(String aSpecFile) {
        final List<String> documents = new ArrayList<>();
        final StringBuilder documentBuilder = new StringBuilder();
        for (String line : aSpecFile.split("\r?\n")) {
            if (line.startsWith(DOCUMENT_DELIMITER)) {
                documents.add(documentBuilder.toString());
                documentBuilder.setLength(0);
            } else {
                documentBuilder.append(line).append(System.lineSeparator());
            }
        }
        if (documentBuilder.length() > 0) {
            documents.add(documentBuilder.toString());
        }
        return documents;
    }

    private static boolean validate(String document) {
        Matcher keyValueMatcher = Pattern.compile("(\\S+):\\s(\\S*)(?:\\b(?!:)|$)").matcher(document);
        return !document.isEmpty() && keyValueMatcher.find();
    }

    private static String readSpecFileFromInputStream(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        try {
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
            return outputStream.toString();
        } catch (IOException e) {
            throw new RuntimeException("Unable to read InputStream." + e);
        }
    }
}
