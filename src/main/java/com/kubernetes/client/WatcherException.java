package com.kubernetes.client;

import java.net.HttpURLConnection;

public class WatcherException extends Exception {

  public WatcherException(String message, Throwable cause) {
    super(message, cause);
  }

  public WatcherException(String message) {
    super(message);
  }

  public KubernetesClientException asClientException() {
    final Throwable cause = getCause();
    return cause instanceof KubernetesClientException ?
      (KubernetesClientException) cause : new KubernetesClientException(getMessage(), cause);
  }

  public boolean isHttpGone() {
    final KubernetesClientException cause = asClientException();
    return cause.getCode() == HttpURLConnection.HTTP_GONE
      || (cause.getStatus() != null && cause.getStatus().getCode() == HttpURLConnection.HTTP_GONE);
  }

  public boolean isShouldRetry() {
    return getCause() == null || !isHttpGone();
  }
}
