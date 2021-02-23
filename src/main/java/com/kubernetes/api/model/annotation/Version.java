package com.kubernetes.api.model.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

@Target({TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Version {

    /**
     * The name of this version.
     *
     * @return the name of this version
     */
    String value();

    /**
     * Whether or not this version corresponds to the persisted version for the associated CRD. Note that only one version can set
     * {@code storage} to {@code true} for a given CRD.
     *
     * @return {@code true} if this version corresponds to the persisted version for the associated CRD, {@code false} otherwise
     */
    boolean storage() default true;

    /**
     * Whether this version is served (i.e. enabled for consumption from the REST API) or not.
     *
     * @return {@code true} if this version is served by the REST API, {@code false} otherwise
     */
    boolean served() default true;
}
