package com.kubernetes.client;

public interface ExtensionAdapter<C> {
    Class<C> getExtensionType();

    Boolean isAdaptable(Client client);

    C adapt(Client client);
}
