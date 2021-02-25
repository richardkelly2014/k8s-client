package com.kubernetes.api.model.apiextensions.v1;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kubernetes.api.model.HasMetadata;
import com.kubernetes.api.model.ObjectMeta;
import com.kubernetes.api.model.annotation.Group;
import com.kubernetes.api.model.annotation.PackageSuffix;
import com.kubernetes.api.model.annotation.Version;

import java.util.HashMap;
import java.util.Map;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "apiVersion",
        "kind",
        "metadata",
        "spec",
        "status"
})
@ToString
@EqualsAndHashCode
@Version("v1")
@Group("apiextensions.k8s.io")
@PackageSuffix(".apiextensions.v1")
public class CustomResourceDefinition implements HasMetadata {

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("apiVersion")
    private String apiVersion = "apiextensions.k8s.io/v1";
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("kind")
    private String kind = "CustomResourceDefinition";
    @JsonProperty("metadata")
    private com.kubernetes.api.model.ObjectMeta metadata;
    @JsonProperty("spec")
    private CustomResourceDefinitionSpec spec;
    @JsonProperty("status")
    private CustomResourceDefinitionStatus status;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


    @Override
    public ObjectMeta getMetadata() {
        return null;
    }

    @Override
    public void setMetadata(ObjectMeta metadata) {

    }

    @Override
    public void setApiVersion(String version) {

    }
}
