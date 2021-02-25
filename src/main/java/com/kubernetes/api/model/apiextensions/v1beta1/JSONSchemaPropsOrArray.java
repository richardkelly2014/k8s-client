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


@JsonSerialize(using = com.kubernetes.api.model.apiextensions.v1beta1.JSONSchemaPropsOrArraySerDe.Serializer.class)
@JsonDeserialize(using = com.kubernetes.api.model.apiextensions.v1beta1.JSONSchemaPropsOrArraySerDe.Deserializer.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "apiVersion",
        "kind",
        "metadata",
        "JSONSchemas",
        "Schema"
})
@ToString
@EqualsAndHashCode
public class JSONSchemaPropsOrArray implements KubernetesResource {
    @JsonProperty("JSONSchemas")
    private List<JSONSchemaProps> jSONSchemas = new ArrayList<JSONSchemaProps>();
    @JsonProperty("Schema")
    private JSONSchemaProps schema;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     */
    public JSONSchemaPropsOrArray() {
    }

    /**
     * @param schema
     * @param jSONSchemas
     */
    public JSONSchemaPropsOrArray(List<JSONSchemaProps> jSONSchemas, JSONSchemaProps schema) {
        super();
        this.jSONSchemas = jSONSchemas;
        this.schema = schema;
    }

    @JsonProperty("JSONSchemas")
    public List<JSONSchemaProps> getJSONSchemas() {
        return jSONSchemas;
    }

    @JsonProperty("JSONSchemas")
    public void setJSONSchemas(List<JSONSchemaProps> jSONSchemas) {
        this.jSONSchemas = jSONSchemas;
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
