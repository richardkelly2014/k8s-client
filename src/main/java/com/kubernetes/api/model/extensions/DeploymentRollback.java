package com.kubernetes.api.model.extensions;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kubernetes.api.model.KubernetesResource;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;


@JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "apiVersion",
        "kind",
        "metadata",
        "name",
        "rollbackTo",
        "updatedAnnotations"
})
@ToString
@EqualsAndHashCode
public class DeploymentRollback implements KubernetesResource {

    /**
     * (Required)
     */
    @JsonProperty("apiVersion")
    private java.lang.String apiVersion = "extensions/v1beta1";
    /**
     * (Required)
     */
    @JsonProperty("kind")
    private java.lang.String kind = "DeploymentRollback";
    @JsonProperty("name")
    private java.lang.String name;
    @JsonProperty("rollbackTo")
    private RollbackConfig rollbackTo;
    @JsonProperty("updatedAnnotations")
    private Map<String, String> updatedAnnotations;
    @JsonIgnore
    private Map<java.lang.String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     */
    public DeploymentRollback() {
    }

    /**
     * @param updatedAnnotations
     * @param apiVersion
     * @param kind
     * @param name
     * @param rollbackTo
     */
    public DeploymentRollback(java.lang.String apiVersion, java.lang.String kind, java.lang.String name, RollbackConfig rollbackTo, Map<String, String> updatedAnnotations) {
        super();
        this.apiVersion = apiVersion;
        this.kind = kind;
        this.name = name;
        this.rollbackTo = rollbackTo;
        this.updatedAnnotations = updatedAnnotations;
    }

    /**
     * (Required)
     */
    @JsonProperty("apiVersion")
    public java.lang.String getApiVersion() {
        return apiVersion;
    }

    /**
     * (Required)
     */
    @JsonProperty("apiVersion")
    public void setApiVersion(java.lang.String apiVersion) {
        this.apiVersion = apiVersion;
    }

    /**
     * (Required)
     */
    @JsonProperty("kind")
    public java.lang.String getKind() {
        return kind;
    }

    /**
     * (Required)
     */
    @JsonProperty("kind")
    public void setKind(java.lang.String kind) {
        this.kind = kind;
    }

    @JsonProperty("name")
    public java.lang.String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(java.lang.String name) {
        this.name = name;
    }

    @JsonProperty("rollbackTo")
    public RollbackConfig getRollbackTo() {
        return rollbackTo;
    }

    @JsonProperty("rollbackTo")
    public void setRollbackTo(RollbackConfig rollbackTo) {
        this.rollbackTo = rollbackTo;
    }

    @JsonProperty("updatedAnnotations")
    public Map<String, String> getUpdatedAnnotations() {
        return updatedAnnotations;
    }

    @JsonProperty("updatedAnnotations")
    public void setUpdatedAnnotations(Map<String, String> updatedAnnotations) {
        this.updatedAnnotations = updatedAnnotations;
    }

    @JsonAnyGetter
    public Map<java.lang.String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(java.lang.String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
