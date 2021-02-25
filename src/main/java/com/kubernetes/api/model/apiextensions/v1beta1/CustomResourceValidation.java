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
     *
     */
    public CustomResourceValidation() {
    }

    /**
     *
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
