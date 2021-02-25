package com.kubernetes.api.model.certificates.v1;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kubernetes.api.model.HasMetadata;
import com.kubernetes.api.model.annotation.Group;
import com.kubernetes.api.model.annotation.PackageSuffix;
import com.kubernetes.api.model.annotation.Version;
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
        "spec",
        "status"
})
@ToString
@EqualsAndHashCode
@Version("v1")
@Group("certificates.k8s.io")
@PackageSuffix(".certificates.v1")
public class CertificateSigningRequest implements HasMetadata {

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("apiVersion")
    private String apiVersion = "certificates.k8s.io/v1";
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("kind")
    private String kind = "CertificateSigningRequest";
    @JsonProperty("metadata")
    private com.kubernetes.api.model.ObjectMeta metadata;
    @JsonProperty("spec")
    private CertificateSigningRequestSpec spec;
    @JsonProperty("status")
    private CertificateSigningRequestStatus status;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public CertificateSigningRequest() {
    }

    /**
     *
     * @param metadata
     * @param apiVersion
     * @param kind
     * @param spec
     * @param status
     */
    public CertificateSigningRequest(String apiVersion, String kind, com.kubernetes.api.model.ObjectMeta metadata, CertificateSigningRequestSpec spec, CertificateSigningRequestStatus status) {
        super();
        this.apiVersion = apiVersion;
        this.kind = kind;
        this.metadata = metadata;
        this.spec = spec;
        this.status = status;
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
    public com.kubernetes.api.model.ObjectMeta getMetadata() {
        return metadata;
    }

    @JsonProperty("metadata")
    public void setMetadata(com.kubernetes.api.model.ObjectMeta metadata) {
        this.metadata = metadata;
    }

    @JsonProperty("spec")
    public CertificateSigningRequestSpec getSpec() {
        return spec;
    }

    @JsonProperty("spec")
    public void setSpec(CertificateSigningRequestSpec spec) {
        this.spec = spec;
    }

    @JsonProperty("status")
    public CertificateSigningRequestStatus getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(CertificateSigningRequestStatus status) {
        this.status = status;
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
