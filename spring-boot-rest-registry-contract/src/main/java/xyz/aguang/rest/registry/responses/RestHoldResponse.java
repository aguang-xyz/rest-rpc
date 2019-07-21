package xyz.aguang.rest.registry.responses;

import java.util.List;
import java.util.Map;
import xyz.aguang.rest.registry.entities.RestServer;

public class RestHoldResponse {

  // Watching providers' info.
  private Map<String, List<RestServer>> watchingProviders;

  // Watching version.
  private long watchingVersion;

  // The server side time while generating hold response.
  private long responseTime;
}
