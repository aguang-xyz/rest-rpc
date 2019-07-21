package xyz.aguang.rest.registry.requests;

import java.util.List;
import xyz.aguang.rest.registry.entities.RestServer;

public class RestHoldRequest {

  // Self info.
  private RestServer self;

  // Is local server still active.
  private boolean selfActive;

  // Current watching providers' names.
  private List<String> watchingProviderNames;

  // Local watching data version.
  private long previousVersion;

  // The client side time while sending hold request.
  private long requestTime;

  // Request timeout in milliseconds.
  private long requestTimeout;
}
