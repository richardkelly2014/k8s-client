package com.kubernetes.client;


import com.kubernetes.client.dsl.AnyNamespaceable;
import com.kubernetes.client.dsl.Namespaceable;
import com.kubernetes.client.dsl.RequestConfigurable;

/**
 * 普通k8s client
 *
 * @param <C>
 */
public interface GenericKubernetesClient<C extends Client> extends Client, KubernetesClient,
        Namespaceable<C>,
        AnyNamespaceable<C>,
        RequestConfigurable<C> {
}
