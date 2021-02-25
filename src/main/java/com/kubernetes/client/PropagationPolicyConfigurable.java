package com.kubernetes.client;

import com.kubernetes.api.model.DeletionPropagation;

/**
 * 传播 配置
 * @param <T>
 */
public interface PropagationPolicyConfigurable<T> {

    T withPropagationPolicy(DeletionPropagation propagationPolicy);
}
