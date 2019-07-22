package xyz.aguang.rest.registry.requests;

import xyz.aguang.rest.registry.entities.RestServer;

import java.io.Serializable;
import java.util.Set;

public class RestRegistryHoldRequest implements Serializable {

  private RestServer from;

  private boolean active;

  private Set<String> watchingNames;

  private long previousModifyTime;

  private long watchTimeout;
}
