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
        "categories",
        "listKind",
        "plural",
        "shortNames",
        "singular"
})
@ToString
@EqualsAndHashCode
public class CustomResourceDefinitionNames implements KubernetesResource {

    @JsonProperty("categories")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> categories = new ArrayList<String>();
    @JsonProperty("kind")
    private String kind;
    @JsonProperty("listKind")
    private String listKind;
    @JsonProperty("plural")
    private String plural;
    @JsonProperty("shortNames")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> shortNames = new ArrayList<String>();
    @JsonProperty("singular")
    private String singular;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     */
    public CustomResourceDefinitionNames() {
    }

    /**
     * @param listKind
     * @param shortNames
     * @param plural
     * @param kind
     * @param categories
     * @param singular
     */
    public CustomResourceDefinitionNames(List<String> categories, String kind, String listKind, String plural, List<String> shortNames, String singular) {
        super();
        this.categories = categories;
        this.kind = kind;
        this.listKind = listKind;
        this.plural = plural;
        this.shortNames = shortNames;
        this.singular = singular;
    }

    @JsonProperty("categories")
    public List<String> getCategories() {
        return categories;
    }

    @JsonProperty("categories")
    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    @JsonProperty("kind")
    public String getKind() {
        return kind;
    }

    @JsonProperty("kind")
    public void setKind(String kind) {
        this.kind = kind;
    }

    @JsonProperty("listKind")
    public String getListKind() {
        return listKind;
    }

    @JsonProperty("listKind")
    public void setListKind(String listKind) {
        this.listKind = listKind;
    }

    @JsonProperty("plural")
    public String getPlural() {
        return plural;
    }

    @JsonProperty("plural")
    public void setPlural(String plural) {
        this.plural = plural;
    }

    @JsonProperty("shortNames")
    public List<String> getShortNames() {
        return shortNames;
    }

    @JsonProperty("shortNames")
    public void setShortNames(List<String> shortNames) {
        this.shortNames = shortNames;
    }

    @JsonProperty("singular")
    public String getSingular() {
        return singular;
    }

    @JsonProperty("singular")
    public void setSingular(String singular) {
        this.singular = singular;
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
