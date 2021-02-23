package com.kubernetes.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kubernetes.api.model.annotation.Group;
import com.kubernetes.api.model.annotation.Kind;
import com.kubernetes.api.model.annotation.Version;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 是否有 MetaData 数据
 */
public interface HasMetadata extends KubernetesResource {
    String DNS_LABEL_START = "(?!-)[A-Za-z0-9-]{";
    String DNS_LABEL_END = ",63}(?<!-)";
    String DNS_LABEL_REGEXP = DNS_LABEL_START + 1 + DNS_LABEL_END;

    Pattern FINALIZER_NAME_MATCHER = Pattern.compile("^((" + DNS_LABEL_REGEXP + "\\.)+" + DNS_LABEL_START + 2 + DNS_LABEL_END + ")/" + DNS_LABEL_REGEXP);

    ObjectMeta getMetadata();

    void setMetadata(ObjectMeta metadata);

    static String getKind(Class<? extends HasMetadata> clazz) {
        final Kind kind = clazz.getAnnotation(Kind.class);
        return kind != null ? kind.value() : clazz.getSimpleName();
    }

    default String getKind() {
        return getKind(getClass());
    }

    static String getApiVersion(Class<? extends HasMetadata> clazz) {
        final String group = getGroup(clazz);
        final String version = getVersion(clazz);
        if (group != null && version != null) {
            return group + "/" + version;
        }
        if (group != null || version != null) {
            throw new IllegalArgumentException("You need to specify both @" + Group.class.getSimpleName() + " and @" + Version.class.getSimpleName() + " annotations if you specify either");
        }
        return null;
    }

    static String getGroup(Class<? extends HasMetadata> clazz) {
        final Group group = clazz.getAnnotation(Group.class);
        return group != null ? group.value() : null;
    }

    static String getVersion(Class<? extends HasMetadata> clazz) {
        final Version version = clazz.getAnnotation(Version.class);
        return version != null ? version.value() : null;
    }

    default String getApiVersion() {
        return getApiVersion(getClass());
    }

    void setApiVersion(String version);

    @JsonIgnore
    default boolean isMarkedForDeletion() {
        final String deletionTimestamp = getMetadata().getDeletionTimestamp();
        return deletionTimestamp != null && !deletionTimestamp.isEmpty();
    }

    default boolean hasFinalizer(String finalizer) {
        return getMetadata().getFinalizers().contains(finalizer);
    }

    default boolean addFinalizer(String finalizer) {
        if (finalizer == null || finalizer.trim().isEmpty()) {
            throw new IllegalArgumentException("Must pass a non-null, non-blank finalizer.");
        }
        if (isMarkedForDeletion() || hasFinalizer(finalizer)) {
            return false;
        }
        if (isFinalizerValid(finalizer)) {
            return getMetadata().getFinalizers().add(finalizer);
        } else {
            throw new IllegalArgumentException("Invalid finalizer name: '" + finalizer + "'. Must consist of a domain name, a forward slash and the valid kubernetes name.");
        }
    }

    default boolean isFinalizerValid(String finalizer) {
        if (finalizer == null) {
            return false;
        }
        final Matcher matcher = FINALIZER_NAME_MATCHER.matcher(finalizer);
        if (matcher.matches()) {
            final String group = matcher.group(1);
            return group.length() < 256;
        } else {
            return false;
        }
    }

    default boolean removeFinalizer(String finalizer) {
        return getMetadata().getFinalizers().remove(finalizer);
    }
}
