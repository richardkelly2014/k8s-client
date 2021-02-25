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
        "openAPIV3Schema"
})
@ToString
@EqualsAndHashCode
public class CustomResourceValidation implements KubernetesResource {

    @JsonProperty("openAPIV3Schema")
    private JSONSchemaProps openAPIV3Schema;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     */
    public CustomResourceValidation() {
    }

    /**
     * @param openAPIV3Schema
     */
    public CustomResourceValidation(JSONSchemaProps openAPIV3Schema) {
        super();
        this.openAPIV3Schema = openAPIV3Schema;
    }

    @JsonProperty("openAPIV3Schema")
    public JSONSchemaProps getOpenAPIV3Schema() {
        return openAPIV3Schema;
    }

    @JsonProperty("openAPIV3Schema")
    public void setOpenAPIV3Schema(JSONSchemaProps openAPIV3Schema) {
        this.openAPIV3Schema = openAPIV3Schema;
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
