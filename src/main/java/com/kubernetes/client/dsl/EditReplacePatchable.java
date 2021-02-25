package com.kubernetes.client.dsl;

public interface EditReplacePatchable<T> extends Editable<T>, Replaceable<T>, Patchable<T>, StatusUpdatable<T> {
}
