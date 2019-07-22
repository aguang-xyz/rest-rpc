package xyz.aguang.rest.registry.responses;

import xyz.aguang.rest.registry.entities.RestServer;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

public class RestHoldResponse implements Serializable {

  private Map<String, Set<RestServer>> modifiedServers;

  private long lastModifyTime;
}
