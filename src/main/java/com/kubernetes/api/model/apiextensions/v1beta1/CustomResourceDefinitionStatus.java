package com.kubernetes.api.model.apiextensions.v1beta1;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kubernetes.api.model.KubernetesResource;
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
        "acceptedNames",
        "conditions",
        "storedVersions"
})
@ToString
@EqualsAndHashCode
public class CustomResourceDefinitionStatus implements KubernetesResource {

    @JsonProperty("acceptedNames")
    private CustomResourceDefinitionNames acceptedNames;
    @JsonProperty("conditions")
    private List<CustomResourceDefinitionCondition> conditions = new ArrayList<CustomResourceDefinitionCondition>();
    @JsonProperty("storedVersions")
    private List<String> storedVersions = new ArrayList<String>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public CustomResourceDefinitionStatus() {
    }

    /**
     *
     * @param storedVersions
     * @param conditions
     * @param acceptedNames
     */
    public CustomResourceDefinitionStatus(CustomResourceDefinitionNames acceptedNames, List<CustomResourceDefinitionCondition> conditions, List<String> storedVersions) {
        super();
        this.acceptedNames = acceptedNames;
        this.conditions = conditions;
        this.storedVersions = storedVersions;
    }

    @JsonProperty("acceptedNames")
    public CustomResourceDefinitionNames getAcceptedNames() {
        return acceptedNames;
    }

    @JsonProperty("acceptedNames")
    public void setAcceptedNames(CustomResourceDefinitionNames acceptedNames) {
        this.acceptedNames = acceptedNames;
    }

    @JsonProperty("conditions")
    public List<CustomResourceDefinitionCondition> getConditions() {
        return conditions;
    }

    @JsonProperty("conditions")
    public void setConditions(List<CustomResourceDefinitionCondition> conditions) {
        this.conditions = conditions;
    }

    @JsonProperty("storedVersions")
    public List<String> getStoredVersions() {
        return storedVersions;
    }

    @JsonProperty("storedVersions")
    public void setStoredVersions(List<String> storedVersions) {
        this.storedVersions = storedVersions;
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
