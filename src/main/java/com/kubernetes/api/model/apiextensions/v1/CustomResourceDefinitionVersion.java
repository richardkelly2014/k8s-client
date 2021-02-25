package com.kubernetes.api.model.apiextensions.v1;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kubernetes.api.model.KubernetesResource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "apiVersion",
        "kind",
        "metadata",
        "additionalPrinterColumns",
        "deprecated",
        "deprecationWarning",
        "name",
        "schema",
        "served",
        "storage",
        "subresources"
})
@ToString
@EqualsAndHashCode
public class CustomResourceDefinitionVersion implements KubernetesResource {

    @JsonProperty("additionalPrinterColumns")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<CustomResourceColumnDefinition> additionalPrinterColumns = new ArrayList<CustomResourceColumnDefinition>();
    @JsonProperty("deprecated")
    private Boolean deprecated;
    @JsonProperty("deprecationWarning")
    private String deprecationWarning;
    @JsonProperty("name")
    private String name;
    @JsonProperty("schema")
    private CustomResourceValidation schema;
    @JsonProperty("served")
    private Boolean served;
    @JsonProperty("storage")
    private Boolean storage;
    @JsonProperty("subresources")
    private CustomResourceSubresources subresources;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     */
    public CustomResourceDefinitionVersion() {
    }

    /**
     * @param schema
     * @param deprecated
     * @param deprecationWarning
     * @param served
     * @param name
     * @param subresources
     * @param storage
     * @param additionalPrinterColumns
     */
    public CustomResourceDefinitionVersion(List<CustomResourceColumnDefinition> additionalPrinterColumns, Boolean deprecated, String deprecationWarning, String name, CustomResourceValidation schema, Boolean served, Boolean storage, CustomResourceSubresources subresources) {
        super();
        this.additionalPrinterColumns = additionalPrinterColumns;
        this.deprecated = deprecated;
        this.deprecationWarning = deprecationWarning;
        this.name = name;
        this.schema = schema;
        this.served = served;
        this.storage = storage;
        this.subresources = subresources;
    }

    @JsonProperty("additionalPrinterColumns")
    public List<CustomResourceColumnDefinition> getAdditionalPrinterColumns() {
        return additionalPrinterColumns;
    }

    @JsonProperty("additionalPrinterColumns")
    public void setAdditionalPrinterColumns(List<CustomResourceColumnDefinition> additionalPrinterColumns) {
        this.additionalPrinterColumns = additionalPrinterColumns;
    }

    @JsonProperty("deprecated")
    public Boolean getDeprecated() {
        return deprecated;
    }

    @JsonProperty("deprecated")
    public void setDeprecated(Boolean deprecated) {
        this.deprecated = deprecated;
    }

    @JsonProperty("deprecationWarning")
    public String getDeprecationWarning() {
        return deprecationWarning;
    }

    @JsonProperty("deprecationWarning")
    public void setDeprecationWarning(String deprecationWarning) {
        this.deprecationWarning = deprecationWarning;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("schema")
    public CustomResourceValidation getSchema() {
        return schema;
    }

    @JsonProperty("schema")
    public void setSchema(CustomResourceValidation schema) {
        this.schema = schema;
    }

    @JsonProperty("served")
    public Boolean getServed() {
        return served;
    }

    @JsonProperty("served")
    public void setServed(Boolean served) {
        this.served = served;
    }

    @JsonProperty("storage")
    public Boolean getStorage() {
        return storage;
    }

    @JsonProperty("storage")
    public void setStorage(Boolean storage) {
        this.storage = storage;
    }

    @JsonProperty("subresources")
    public CustomResourceSubresources getSubresources() {
        return subresources;
    }

    @JsonProperty("subresources")
    public void setSubresources(CustomResourceSubresources subresources) {
        this.subresources = subresources;
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
