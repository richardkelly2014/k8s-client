package com.kubernetes.client.dsl;

import java.util.function.Function;

public interface FunctionCallable<I> {

    <O> O call(Function<I, O> function);
}
