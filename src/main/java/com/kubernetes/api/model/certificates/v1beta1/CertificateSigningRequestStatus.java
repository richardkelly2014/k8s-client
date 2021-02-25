package com.kubernetes.api.model.certificates.v1beta1;

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
        "certificate",
        "conditions"
})
@ToString
@EqualsAndHashCode
public class CertificateSigningRequestStatus implements KubernetesResource {
    @JsonProperty("certificate")
    private String certificate;
    @JsonProperty("conditions")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<CertificateSigningRequestCondition> conditions = new ArrayList<CertificateSigningRequestCondition>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public CertificateSigningRequestStatus() {
    }

    /**
     *
     * @param certificate
     * @param conditions
     */
    public CertificateSigningRequestStatus(String certificate, List<CertificateSigningRequestCondition> conditions) {
        super();
        this.certificate = certificate;
        this.conditions = conditions;
    }

    @JsonProperty("certificate")
    public String getCertificate() {
        return certificate;
    }

    @JsonProperty("certificate")
    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    @JsonProperty("conditions")
    public List<CertificateSigningRequestCondition> getConditions() {
        return conditions;
    }

    @JsonProperty("conditions")
    public void setConditions(List<CertificateSigningRequestCondition> conditions) {
        this.conditions = conditions;
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
