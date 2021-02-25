package com.kubernetes.client.dsl;

import com.kubernetes.api.builder.Visitor;

import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public interface Editable<T> {

    T edit(UnaryOperator<T> function);

    T edit(Visitor... visitors);

    <V> T edit(Class<V> visitorType, Visitor<V> visitor);

    T accept(Consumer<T> function);
}
