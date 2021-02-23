package com.kubernetes.client;

import com.kubernetes.api.model.Status;
import com.kubernetes.client.util.Utils;

public class KubernetesClientException extends RuntimeException {
    private int code;
    private Status status;

    public KubernetesClientException(String message) {
        super(message);
    }

    public KubernetesClientException(String message, Throwable t) {
        super(message, t);
    }

    public KubernetesClientException(Status status) {
        this(status.getMessage(), status.getCode(), status);
    }

    public KubernetesClientException(String message, int code, Status status) {
        super(message);
        this.code = code;
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public int getCode() {
        return code;
    }

    public static RuntimeException launderThrowable(Throwable cause) {
        return launderThrowable("An error has occurred.", cause);
    }

    public static RuntimeException launderThrowable(String message, Throwable cause) {
        if (cause instanceof RuntimeException) {
            return ((RuntimeException) cause);
        } else if (cause instanceof Error) {
            throw ((Error) cause);
        } else if (cause instanceof InterruptedException) {
            Thread.currentThread().interrupt();
        }
        throw new KubernetesClientException(message, cause);
    }

    public static RuntimeException launderThrowable(OperationInfo spec, Throwable cause) {
        if (cause instanceof KubernetesClientException) {
            return launderThrowable(spec, ((KubernetesClientException) cause).getStatus(), cause);
        }

        return launderThrowable(describeOperation(spec) + " failed.", cause);
    }

    public static RuntimeException launderThrowable(OperationInfo spec, Status status, Throwable cause) {
        StringBuilder sb = new StringBuilder();
        sb.append(describeOperation(spec) + " failed.");
        if (status != null && Utils.isNotNullOrEmpty(status.getMessage())) {
            sb.append("Reason: ").append(status.getMessage());
        }
        return launderThrowable(sb.toString(), cause);
    }

    private static final String describeOperation(OperationInfo operation) {
        StringBuilder sb = new StringBuilder();
        sb.append("Operation");
        if (Utils.isNotNullOrEmpty(operation.getOperationType())) {
            sb.append(": [").append(operation.getOperationType() + "]");
        }
        sb.append(" ");
        sb.append(" for kind: [").append(operation.getKind()).append("] ");
        sb.append(" with name: [").append(operation.getName()).append("] ");
        sb.append(" in namespace: [").append(operation.getNamespace()).append("] ");
        return sb.toString();
    }
}
