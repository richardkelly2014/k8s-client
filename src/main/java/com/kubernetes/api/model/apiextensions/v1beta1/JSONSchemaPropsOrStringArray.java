package com.kubernetes.api.model.apiextensions.v1beta1;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.kubernetes.api.model.KubernetesResource;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonSerialize(using = com.kubernetes.api.model.apiextensions.v1beta1.JSONSchemaPropsOrStringArraySerDe.Serializer.class)
@JsonDeserialize(using = com.kubernetes.api.model.apiextensions.v1beta1.JSONSchemaPropsOrStringArraySerDe.Deserializer.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "apiVersion",
        "kind",
        "metadata",
        "Property",
        "Schema"
})
@ToString
@EqualsAndHashCode
public class JSONSchemaPropsOrStringArray implements KubernetesResource {
    @JsonProperty("Property")
    private List<String> property = new ArrayList<String>();
    @JsonProperty("Schema")
    private JSONSchemaProps schema;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public JSONSchemaPropsOrStringArray() {
    }

    /**
     *
     * @param schema
     * @param property
     */
    public JSONSchemaPropsOrStringArray(List<String> property, JSONSchemaProps schema) {
        super();
        this.property = property;
        this.schema = schema;
    }

    @JsonProperty("Property")
    public List<String> getProperty() {
        return property;
    }

    @JsonProperty("Property")
    public void setProperty(List<String> property) {
        this.property = property;
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
