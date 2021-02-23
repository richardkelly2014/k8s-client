package com.kubernetes.internal;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.kubernetes.api.model.KubernetesResource;

import java.io.IOException;

public class KubernetesDeserializer extends JsonDeserializer<KubernetesResource> {
    @Override
    public KubernetesResource deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return null;
    }
}
