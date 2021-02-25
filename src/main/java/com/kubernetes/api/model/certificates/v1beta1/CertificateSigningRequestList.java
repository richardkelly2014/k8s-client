package com.kubernetes.api.model.certificates.v1beta1;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kubernetes.api.model.KubernetesResource;
import com.kubernetes.api.model.KubernetesResourceList;
import com.kubernetes.api.model.ListMeta;
import com.kubernetes.api.model.annotation.Group;
import com.kubernetes.api.model.annotation.PackageSuffix;
import com.kubernetes.api.model.annotation.Version;
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
        "items"
})
@ToString
@EqualsAndHashCode
@Version("v1beta1")
@Group("certificates.k8s.io")
@PackageSuffix(".certificates.v1beta1")
public class CertificateSigningRequestList implements KubernetesResource, KubernetesResourceList<CertificateSigningRequest> {
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("apiVersion")
    private String apiVersion = "certificates.k8s.io/v1beta1";
    @JsonProperty("items")
    private List<com.kubernetes.api.model.certificates.v1beta1.CertificateSigningRequest> items = new ArrayList<com.kubernetes.api.model.certificates.v1beta1.CertificateSigningRequest>();
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("kind")
    private String kind = "CertificateSigningRequestList";
    @JsonProperty("metadata")
    private ListMeta metadata;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public CertificateSigningRequestList() {
    }

    /**
     *
     * @param metadata
     * @param apiVersion
     * @param kind
     * @param items
     */
    public CertificateSigningRequestList(String apiVersion, List<com.kubernetes.api.model.certificates.v1beta1.CertificateSigningRequest> items, String kind, ListMeta metadata) {
        super();
        this.apiVersion = apiVersion;
        this.items = items;
        this.kind = kind;
        this.metadata = metadata;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("apiVersion")
    public String getApiVersion() {
        return apiVersion;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("apiVersion")
    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    @JsonProperty("items")
    public List<com.kubernetes.api.model.certificates.v1beta1.CertificateSigningRequest> getItems() {
        return items;
    }

    @JsonProperty("items")
    public void setItems(List<com.kubernetes.api.model.certificates.v1beta1.CertificateSigningRequest> items) {
        this.items = items;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("kind")
    public String getKind() {
        return kind;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("kind")
    public void setKind(String kind) {
        this.kind = kind;
    }

    @JsonProperty("metadata")
    public ListMeta getMetadata() {
        return metadata;
    }

    @JsonProperty("metadata")
    public void setMetadata(ListMeta metadata) {
        this.metadata = metadata;
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
