package com.kubernetes.api.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kubernetes.internal.KubernetesDeserializer;

import java.io.Serializable;

/**
 * k8s 资源 , 自定义反序列化
 */
@JsonDeserialize(using = KubernetesDeserializer.class)
public interface KubernetesResource extends Serializable {
}
