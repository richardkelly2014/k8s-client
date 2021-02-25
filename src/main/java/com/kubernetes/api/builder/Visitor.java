package com.kubernetes.api.builder;

@FunctionalInterface
public interface Visitor<T> {


    void visit(T element);
}
