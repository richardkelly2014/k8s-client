package com.kubernetes.client.dsl;

import com.kubernetes.client.GracePeriodConfigurable;
import com.kubernetes.client.PropagationPolicyConfigurable;

/**
 * 观察列表删除
 *
 * @param <T>
 * @param <L>
 */
public interface WatchListDeletable<T, L> extends VersionWatchAndWaitable<T>, Listable<L>, Deletable,
        GracePeriodConfigurable<Deletable>,
        PropagationPolicyConfigurable<EditReplacePatchDeletable<T>>,
        StatusUpdatable<T> {
}
