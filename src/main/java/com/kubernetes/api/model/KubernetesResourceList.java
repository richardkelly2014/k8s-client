package com.kubernetes.api.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kubernetes.internal.KubernetesDeserializer;

import java.io.Serializable;
import java.util.List;

@JsonDeserialize(using = KubernetesDeserializer.class)
public interface KubernetesResourceList<E extends com.kubernetes.api.model.HasMetadata> extends Serializable {

    ListMeta getMetadata();

    List<E> getItems();

}
