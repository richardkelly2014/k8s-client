package com.kubernetes.client.dsl;

import com.kubernetes.client.ResourceNotFoundException;

public interface Requirable<T> {

    T require() throws ResourceNotFoundException;
}
