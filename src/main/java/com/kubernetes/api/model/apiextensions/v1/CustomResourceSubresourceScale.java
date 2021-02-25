package com.kubernetes.api.model.apiextensions.v1;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kubernetes.api.model.KubernetesResource;

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
        "labelSelectorPath",
        "specReplicasPath",
        "statusReplicasPath"
})
@ToString
@EqualsAndHashCode
public class CustomResourceSubresourceScale implements KubernetesResource {

    @JsonProperty("labelSelectorPath")
    private String labelSelectorPath;
    @JsonProperty("specReplicasPath")
    private String specReplicasPath;
    @JsonProperty("statusReplicasPath")
    private String statusReplicasPath;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     */
    public CustomResourceSubresourceScale() {
    }

    /**
     * @param statusReplicasPath
     * @param labelSelectorPath
     * @param specReplicasPath
     */
    public CustomResourceSubresourceScale(String labelSelectorPath, String specReplicasPath, String statusReplicasPath) {
        super();
        this.labelSelectorPath = labelSelectorPath;
        this.specReplicasPath = specReplicasPath;
        this.statusReplicasPath = statusReplicasPath;
    }

    @JsonProperty("labelSelectorPath")
    public String getLabelSelectorPath() {
        return labelSelectorPath;
    }

    @JsonProperty("labelSelectorPath")
    public void setLabelSelectorPath(String labelSelectorPath) {
        this.labelSelectorPath = labelSelectorPath;
    }

    @JsonProperty("specReplicasPath")
    public String getSpecReplicasPath() {
        return specReplicasPath;
    }

    @JsonProperty("specReplicasPath")
    public void setSpecReplicasPath(String specReplicasPath) {
        this.specReplicasPath = specReplicasPath;
    }

    @JsonProperty("statusReplicasPath")
    public String getStatusReplicasPath() {
        return statusReplicasPath;
    }

    @JsonProperty("statusReplicasPath")
    public void setStatusReplicasPath(String statusReplicasPath) {
        this.statusReplicasPath = statusReplicasPath;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
