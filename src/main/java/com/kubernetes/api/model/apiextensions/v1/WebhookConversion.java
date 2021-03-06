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
        "clientConfig",
        "conversionReviewVersions"
})
@ToString
@EqualsAndHashCode
public class WebhookConversion implements KubernetesResource {

    @JsonProperty("clientConfig")
    private WebhookClientConfig clientConfig;
    @JsonProperty("conversionReviewVersions")
    private List<String> conversionReviewVersions = new ArrayList<String>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     */
    public WebhookConversion() {
    }

    /**
     * @param clientConfig
     * @param conversionReviewVersions
     */
    public WebhookConversion(WebhookClientConfig clientConfig, List<String> conversionReviewVersions) {
        super();
        this.clientConfig = clientConfig;
        this.conversionReviewVersions = conversionReviewVersions;
    }

    @JsonProperty("clientConfig")
    public WebhookClientConfig getClientConfig() {
        return clientConfig;
    }

    @JsonProperty("clientConfig")
    public void setClientConfig(WebhookClientConfig clientConfig) {
        this.clientConfig = clientConfig;
    }

    @JsonProperty("conversionReviewVersions")
    public List<String> getConversionReviewVersions() {
        return conversionReviewVersions;
    }

    @JsonProperty("conversionReviewVersions")
    public void setConversionReviewVersions(List<String> conversionReviewVersions) {
        this.conversionReviewVersions = conversionReviewVersions;
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
