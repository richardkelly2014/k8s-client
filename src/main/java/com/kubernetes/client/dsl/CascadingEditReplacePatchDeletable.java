package com.kubernetes.client.dsl;

import com.kubernetes.client.PropagationPolicyConfigurable;

public interface CascadingEditReplacePatchDeletable<T> extends
        EditReplacePatchDeletable<T>,
        Cascading<EditReplacePatchDeletable<T>>,
        PropagationPolicyConfigurable<EditReplacePatchDeletable<T>>,
        Lockable<Replaceable<T>> {
}
