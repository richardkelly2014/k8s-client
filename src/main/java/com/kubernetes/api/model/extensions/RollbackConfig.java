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
        "revision"
})
@ToString
@EqualsAndHashCode
public class RollbackConfig implements KubernetesResource {

    @JsonProperty("revision")
    private Long revision;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     */
    public RollbackConfig() {
    }

    /**
     * @param revision
     */
    public RollbackConfig(Long revision) {
        super();
        this.revision = revision;
    }

    @JsonProperty("revision")
    public Long getRevision() {
        return revision;
    }

    @JsonProperty("revision")
    public void setRevision(Long revision) {
        this.revision = revision;
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
