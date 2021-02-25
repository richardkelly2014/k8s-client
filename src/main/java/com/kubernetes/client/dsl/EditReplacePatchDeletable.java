package com.kubernetes.client.dsl;

import com.kubernetes.client.GracePeriodConfigurable;

public interface EditReplacePatchDeletable<T> extends EditReplacePatchable<T>, Deletable,
        GracePeriodConfigurable<Deletable> {
}
