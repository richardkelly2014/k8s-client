package com.kubernetes.client.dsl;

public interface Resource<T> extends CreateOrReplaceable<T>,
        CreateFromServerGettable<T>,
        CascadingEditReplacePatchDeletable<T>,
        VersionWatchAndWaitable<T>,
        Requirable<T>, Readiable {
}
