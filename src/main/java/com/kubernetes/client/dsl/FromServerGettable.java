package com.kubernetes.client.dsl;

public interface FromServerGettable<T> extends Gettable<T>, FromServerable<Gettable<T>> {
}
