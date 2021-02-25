package com.kubernetes.api.model.apiextensions.v1;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kubernetes.api.model.KubernetesResource;
import com.kubernetes.api.model.KubernetesResourceList;
import com.kubernetes.api.model.annotation.Group;
import com.kubernetes.api.model.annotation.PackageSuffix;
import com.kubernetes.api.model.annotation.Version;

import lombok.EqualsAndHashCode;
import lombok.ToString;

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
@Version("v1")
@Group("apiextensions.k8s.io")
@PackageSuffix(".apiextensions.v1")
public class CustomResourceDefinitionList implements KubernetesResource, ˚KubernetesResourceList<CustomResourceDefinition> {
}
