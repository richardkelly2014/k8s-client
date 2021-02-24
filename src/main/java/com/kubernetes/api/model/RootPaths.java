package com.kubernetes.api.model;


import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "apiVersion",
        "kind",
        "metadata",
        "paths"
})
@ToString
@EqualsAndHashCode
public class RootPaths implements KubernetesResource{
    @JsonProperty("paths")
    private List<String> paths = new ArrayList<String>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public RootPaths() {
    }

    /**
     *
     * @param paths
     */
    public RootPaths(List<String> paths) {
        super();
        this.paths = paths;
    }

    @JsonProperty("paths")
    public List<String> getPaths() {
        return paths;
    }

    @JsonProperty("paths")
    public void setPaths(List<String> paths) {
        this.paths = paths;
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
