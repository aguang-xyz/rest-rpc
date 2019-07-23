package xyz.aguang.rest.registry.responses;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import xyz.aguang.rest.registry.entities.RestServer;

public class RestRegistryHoldResponse implements Serializable {

  private Map<String, Set<RestServer>> modifiedServers;

  private Map<String, Long> lastModifyTimes;

  public static RestRegistryHoldResponse create(
      Map<String, Set<RestServer>> modifiedServers, Map<String, Long> lastModifyTimes) {

    RestRegistryHoldResponse response = new RestRegistryHoldResponse();

    response.setModifiedServers(modifiedServers);
    response.setLastModifyTimes(lastModifyTimes);

    return response;
  }

  public Map<String, Set<RestServer>> getModifiedServers() {
    return modifiedServers;
  }

  public void setModifiedServers(Map<String, Set<RestServer>> modifiedServers) {
    this.modifiedServers = modifiedServers;
  }

  public Map<String, Long> getLastModifyTimes() {
    return lastModifyTimes;
  }

  public void setLastModifyTimes(Map<String, Long> lastModifyTimes) {
    this.lastModifyTimes = lastModifyTimes;
  }
}
