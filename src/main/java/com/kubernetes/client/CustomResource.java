package com.kubernetes.client;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import com.kubernetes.api.model.HasMetadata;
import com.kubernetes.api.model.Namespaced;
import com.kubernetes.api.model.ObjectMeta;
import com.kubernetes.api.model.annotation.Group;
import com.kubernetes.api.model.annotation.Plural;
import com.kubernetes.api.model.annotation.Singular;
import com.kubernetes.api.model.annotation.Version;
import com.kubernetes.client.util.Pluralize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.kubernetes.client.util.Utils.isNullOrEmpty;

@JsonDeserialize(
        using = JsonDeserializer.None.class
)
@JsonPropertyOrder({
        "apiVersion",
        "kind",
        "metadata",
        "spec",
        "status"
})
public abstract class CustomResource<S, T> implements HasMetadata {
    private static final Logger LOG = LoggerFactory.getLogger(CustomResource.class);

    public static final String NAMESPACE_SCOPE = "Namespaced";
    public static final String CLUSTER_SCOPE = "Cluster";
    private ObjectMeta metadata = new ObjectMeta();

    @JsonProperty("spec")
    protected S spec;

    @JsonProperty("status")
    protected T status;

    private final String singular;
    private final String crdName;
    private final String kind;
    private final String apiVersion;
    private final String scope;
    private final String plural;
    private final boolean served;
    private final boolean storage;

    public CustomResource() {
        final String version = HasMetadata.super.getApiVersion();
        final Class<? extends CustomResource> clazz = getClass();
        if (isNullOrEmpty(version)) {
            throw new IllegalArgumentException(clazz.getName() + " CustomResource must provide an API version using @"
                    + Group.class.getName() + " and @" + Version.class.getName() + " annotations");
        }
        this.apiVersion = version;
        this.kind = HasMetadata.super.getKind();
        scope = this instanceof Namespaced ? NAMESPACE_SCOPE : CLUSTER_SCOPE;
        this.singular = getSingular(clazz);
        this.plural = getPlural(clazz);
        this.crdName = getCRDName(clazz);
        this.served = getServed(clazz);
        this.storage = getStorage(clazz);
        this.spec = initSpec();
        this.status = initStatus();
    }

    public static boolean getServed(Class<? extends CustomResource> clazz) {
        final Version annotation = clazz.getAnnotation(Version.class);
        return annotation == null || annotation.served();
    }

    public static boolean getStorage(Class<? extends CustomResource> clazz) {
        final Version annotation = clazz.getAnnotation(Version.class);
        return annotation == null || annotation.storage();
    }

    protected S initSpec() {
        return (S) genericInit(0);
    }

    protected T initStatus() {
        return (T) genericInit(1);
    }

    @Override
    public String toString() {
        return "CustomResource{" +
                "kind='" + getKind() + '\'' +
                ", apiVersion='" + getApiVersion() + '\'' +
                ", metadata=" + metadata +
                ", spec=" + spec +
                ", status=" + status +
                '}';
    }

    @Override
    public String getApiVersion() {
        return apiVersion;
    }

    @Override
    public void setApiVersion(String version) {
        // already set in constructor
        LOG.debug("Calling CustomResource#setApiVersion doesn't do anything because the API version is computed and shouldn't be changed");
    }

    @Override
    public String getKind() {
        return this.kind;
    }

    public void setKind(String kind) {
        // already set in constructor
        LOG.debug("Calling CustomResource#setKind doesn't do anything because the Kind is computed and shouldn't be changed");
    }

    @Override
    public ObjectMeta getMetadata() {
        return metadata;
    }

    @Override
    public void setMetadata(ObjectMeta metadata) {
        this.metadata = metadata;
    }

    public static String getPlural(Class<? extends HasMetadata> clazz) {
        final Plural fromAnnotation = clazz.getAnnotation(Plural.class);
        return (fromAnnotation != null ? fromAnnotation.value().toLowerCase(Locale.ROOT) : Pluralize.toPlural(getSingular(clazz)));
    }

    @JsonIgnore
    public String getPlural() {
        return plural;
    }

    public static String getSingular(Class<? extends HasMetadata> clazz) {
        final Singular fromAnnotation = clazz.getAnnotation(Singular.class);
        return (fromAnnotation != null ? fromAnnotation.value() : HasMetadata.getKind(clazz)).toLowerCase(Locale.ROOT);
    }

    @JsonIgnore
    public String getSingular() {
        return singular;
    }

    public static String getCRDName(Class<? extends CustomResource> clazz) {
        return getPlural(clazz) + "." + HasMetadata.getGroup(clazz);
    }

    @JsonIgnore
    public String getCRDName() {
        return crdName;
    }

    @JsonIgnore
    public String getScope() {
        return scope;
    }

    @JsonIgnore
    public String getGroup() {
        return HasMetadata.getGroup(getClass());
    }

    @JsonIgnore
    public String getVersion() {
        return HasMetadata.getVersion(getClass());
    }

    @JsonIgnore
    public boolean isServed() {
        return served;
    }

    @JsonIgnore
    public boolean isStorage() {
        return storage;
    }

    public S getSpec() {
        return spec;
    }

    public void setSpec(S spec) {
        this.spec = spec;
    }

    public T getStatus() {
        return status;
    }

    public void setStatus(T status) {
        this.status = status;
    }

    private final static String TYPE_NAME = CustomResource.class.getTypeName();
    private final static String VOID_TYPE_NAME = Void.class.getTypeName();
    private final static Map<String, Instantiator> instantiators = new ConcurrentHashMap<>();

    @FunctionalInterface
    private interface Instantiator {

        Object instantiate() throws Exception;

        /**
         * No-op instantiator.
         */
        Instantiator NULL = () -> null;
    }

    private Instantiator getInstantiator(int genericTypeIndex) throws Exception {
        final String key = getKey(getClass(), genericTypeIndex);
        Instantiator instantiator = instantiators.get(key);
        if (instantiator == null) {
            instantiator = Instantiator.NULL;

            // walk the type hierarchy until we reach CustomResource or a ParameterizedType
            Type genericSuperclass = getClass().getGenericSuperclass();
            String typeName = genericSuperclass.getTypeName();
            while (!TYPE_NAME.equals(typeName) && !(genericSuperclass instanceof ParameterizedType)) {
                genericSuperclass = ((Class) genericSuperclass).getGenericSuperclass();
                typeName = genericSuperclass.getTypeName();
            }

            // this works because CustomResource is an abstract class
            if (genericSuperclass instanceof ParameterizedType) {
                final Type[] types = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
                if (types.length != 2) {
                    throw new IllegalArgumentException(
                            "Automatic instantiation of Spec and Status only works for CustomResource implementations parameterized with both types, consider overriding initSpec and/or initStatus");
                }
                // get the associated class from the type name, if not Void
                String className = types[genericTypeIndex].getTypeName();
                if (!VOID_TYPE_NAME.equals(className)) {
                    final Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
                    if (clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers())) {
                        throw new IllegalArgumentException(
                                "Cannot instantiate interface/abstract type " + className);
                    }

                    // record the instantiator associated with the identified type
                    instantiator = () -> {
                        final Constructor<?> constructor;
                        try {
                            // get the no-arg (declared needed if implicit) constructor
                            constructor = clazz.getDeclaredConstructor();
                            constructor.setAccessible(true); // and make it accessible
                        } catch (NoSuchMethodException | SecurityException e) {
                            throw new IllegalArgumentException(
                                    "Cannot find a no-arg constructor for " + className);
                        }
                        return constructor.newInstance();
                    };
                }
            }
            instantiators.put(key, instantiator);
        }
        return instantiator;
    }

    private Object genericInit(int genericTypeIndex) {
        try {
            return getInstantiator(genericTypeIndex).instantiate();
        } catch (Exception e) {
            final String fieldName = genericTypeIndex == 0 ? "Spec" : "Status";
            throw new IllegalArgumentException(
                    "Cannot instantiate " + fieldName + ", consider overriding init" + fieldName + ": "
                            + e.getMessage(), e);
        }
    }

    private final static String getKey(Class<? extends CustomResource> clazz, int genericTypeIndex) {
        return clazz.getCanonicalName() + "_" + genericTypeIndex;
    }

}
