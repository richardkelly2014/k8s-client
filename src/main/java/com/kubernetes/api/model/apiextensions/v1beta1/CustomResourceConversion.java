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
        "conversionReviewVersions",
        "strategy",
        "webhookClientConfig"
})
@ToString
@EqualsAndHashCode
public class CustomResourceConversion implements KubernetesResource {

    @JsonProperty("conversionReviewVersions")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> conversionReviewVersions = new ArrayList<String>();
    @JsonProperty("strategy")
    private String strategy;
    @JsonProperty("webhookClientConfig")
    private WebhookClientConfig webhookClientConfig;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public CustomResourceConversion() {
    }

    /**
     *
     * @param webhookClientConfig
     * @param conversionReviewVersions
     * @param strategy
     */
    public CustomResourceConversion(List<String> conversionReviewVersions, String strategy, WebhookClientConfig webhookClientConfig) {
        super();
        this.conversionReviewVersions = conversionReviewVersions;
        this.strategy = strategy;
        this.webhookClientConfig = webhookClientConfig;
    }

    @JsonProperty("conversionReviewVersions")
    public List<String> getConversionReviewVersions() {
        return conversionReviewVersions;
    }

    @JsonProperty("conversionReviewVersions")
    public void setConversionReviewVersions(List<String> conversionReviewVersions) {
        this.conversionReviewVersions = conversionReviewVersions;
    }

    @JsonProperty("strategy")
    public String getStrategy() {
        return strategy;
    }

    @JsonProperty("strategy")
    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    @JsonProperty("webhookClientConfig")
    public WebhookClientConfig getWebhookClientConfig() {
        return webhookClientConfig;
    }

    @JsonProperty("webhookClientConfig")
    public void setWebhookClientConfig(WebhookClientConfig webhookClientConfig) {
        this.webhookClientConfig = webhookClientConfig;
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
