package com.kubernetes.api.model.autoscaling.v1;

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
        "replicas",
        "selector"
})
@ToString
@EqualsAndHashCode
public class ScaleStatus implements KubernetesResource {

    @JsonProperty("replicas")
    private Integer replicas;
    @JsonProperty("selector")
    private String selector;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     */
    public ScaleStatus() {
    }

    /**
     * @param replicas
     * @param selector
     */
    public ScaleStatus(Integer replicas, String selector) {
        super();
        this.replicas = replicas;
        this.selector = selector;
    }

    @JsonProperty("replicas")
    public Integer getReplicas() {
        return replicas;
    }

    @JsonProperty("replicas")
    public void setReplicas(Integer replicas) {
        this.replicas = replicas;
    }

    @JsonProperty("selector")
    public String getSelector() {
        return selector;
    }

    @JsonProperty("selector")
    public void setSelector(String selector) {
        this.selector = selector;
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
