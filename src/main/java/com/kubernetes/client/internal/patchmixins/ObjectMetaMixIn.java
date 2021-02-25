package com.kubernetes.client.internal.patchmixins;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kubernetes.api.model.ObjectMeta;

public abstract class ObjectMetaMixIn extends ObjectMeta {
    @JsonIgnore
    private String creationTimestamp;
    @JsonIgnore
    private String deletionTimestamp;
    @JsonIgnore
    private Long generation;
    @JsonIgnore
    private String resourceVersion;
    @JsonIgnore
    private String selfLink;
    @JsonIgnore
    private String uid;

    @Override
    @JsonIgnore
    public abstract String getCreationTimestamp();

    @Override
    @JsonIgnore
    public abstract String getDeletionTimestamp();

    @Override
    @JsonIgnore
    public abstract Long getGeneration();

    @Override
    @JsonIgnore
    public abstract String getResourceVersion();

    @Override
    @JsonIgnore
    public abstract String getSelfLink();

    @Override
    @JsonIgnore
    public abstract String getUid();
}
