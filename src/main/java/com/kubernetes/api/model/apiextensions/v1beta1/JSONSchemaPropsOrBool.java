package com.kubernetes.api.model.apiextensions.v1beta1;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.kubernetes.api.model.KubernetesResource;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;


@JsonSerialize(using = com.kubernetes.api.model.apiextensions.v1beta1.JSONSchemaPropsOrBoolSerDe.Serializer.class)
@JsonDeserialize(using = com.kubernetes.api.model.apiextensions.v1beta1.JSONSchemaPropsOrBoolSerDe.Deserializer.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "apiVersion",
        "kind",
        "metadata",
        "Allows",
        "Schema"
})
@ToString
@EqualsAndHashCode
public class JSONSchemaPropsOrBool implements KubernetesResource {

    @JsonProperty("Allows")
    private Boolean allows;
    @JsonProperty("Schema")
    private JSONSchemaProps schema;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public JSONSchemaPropsOrBool() {
    }

    /**
     *
     * @param allows
     * @param schema
     */
    public JSONSchemaPropsOrBool(Boolean allows, JSONSchemaProps schema) {
        super();
        this.allows = allows;
        this.schema = schema;
    }

    @JsonProperty("Allows")
    public Boolean getAllows() {
        return allows;
    }

    @JsonProperty("Allows")
    public void setAllows(Boolean allows) {
        this.allows = allows;
    }

    @JsonProperty("Schema")
    public JSONSchemaProps getSchema() {
        return schema;
    }

    @JsonProperty("Schema")
    public void setSchema(JSONSchemaProps schema) {
        this.schema = schema;
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
