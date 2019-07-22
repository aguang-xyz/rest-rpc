package xyz.aguang.rest.registry.responses;

import xyz.aguang.rest.registry.entities.RestServer;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

public class RestRegistryHoldResponse implements Serializable {

  private Map<String, Set<RestServer>> modifiedServers;

  private long lastModifyTime;

  public static RestRegistryHoldResponse create(
      Map<String, Set<RestServer>> modifiedServers, long lastModifyTime) {

    RestRegistryHoldResponse response = new RestRegistryHoldResponse();

    response.setModifiedServers(modifiedServers);
    response.setLastModifyTime(lastModifyTime);

    return response;
  }

  public Map<String, Set<RestServer>> getModifiedServers() {
    return modifiedServers;
  }

  public void setModifiedServers(Map<String, Set<RestServer>> modifiedServers) {
    this.modifiedServers = modifiedServers;
  }

  public long getLastModifyTime() {
    return lastModifyTime;
  }

  public void setLastModifyTime(long lastModifyTime) {
    this.lastModifyTime = lastModifyTime;
  }
}
