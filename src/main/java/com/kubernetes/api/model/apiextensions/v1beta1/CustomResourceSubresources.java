package com.kubernetes.api.model.apiextensions.v1beta1;

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
        "scale",
        "status"
})
@ToString
@EqualsAndHashCode
public class CustomResourceSubresources implements KubernetesResource {
    @JsonProperty("scale")
    private CustomResourceSubresourceScale scale;
    @JsonProperty("status")
    private CustomResourceSubresourceStatus status;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public CustomResourceSubresources() {
    }

    /**
     *
     * @param scale
     * @param status
     */
    public CustomResourceSubresources(CustomResourceSubresourceScale scale, CustomResourceSubresourceStatus status) {
        super();
        this.scale = scale;
        this.status = status;
    }

    @JsonProperty("scale")
    public CustomResourceSubresourceScale getScale() {
        return scale;
    }

    @JsonProperty("scale")
    public void setScale(CustomResourceSubresourceScale scale) {
        this.scale = scale;
    }

    @JsonProperty("status")
    public CustomResourceSubresourceStatus getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(CustomResourceSubresourceStatus status) {
        this.status = status;
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
