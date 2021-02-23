package com.kubernetes.client;

/**
 * 操作详情
 */
public interface OperationInfo {

    //Kind
    String getKind();

    //name
    String getName();

    //namespace
    String getNamespace();

    String getOperationType();

    OperationInfo forOperationType(String type);
}
