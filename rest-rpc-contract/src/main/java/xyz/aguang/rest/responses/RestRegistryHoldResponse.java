package xyz.aguang.rest.responses;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import xyz.aguang.rest.entities.RestServer;

/**
 * Hold response of rest registry.
 *
 * @author Grey King
 */
public class RestRegistryHoldResponse implements Serializable {

  private Map<String, Set<RestServer>> modifiedServers;

  private Map<String, Long> lastModifyTimes;

  /**
   * Create a hold response.
   *
   * @param modifiedServers modified servers.
   * @param lastModifyTimes a map of last modify times, where the key is the name of rest server and
   *     the value is the last modify time(in milliseconds).
   * @return hold response.
   */
  public static RestRegistryHoldResponse create(
      Map<String, Set<RestServer>> modifiedServers, Map<String, Long> lastModifyTimes) {

    RestRegistryHoldResponse response = new RestRegistryHoldResponse();

    response.setModifiedServers(modifiedServers);
    response.setLastModifyTimes(lastModifyTimes);

    return response;
  }

  /**
   * Get the map of modified servers. Where the key is the name of rest server and the value is a
   * set of current instances.
   *
   * @return a map of modified servers.
   */
  public Map<String, Set<RestServer>> getModifiedServers() {
    return modifiedServers;
  }

  /**
   * Set the map of modified servers. Where the key is the name of rest server and the value is a
   * set of current instances.
   *
   * @param modifiedServers
   */
  public void setModifiedServers(Map<String, Set<RestServer>> modifiedServers) {
    this.modifiedServers = modifiedServers;
  }

  /**
   * Get the map of last modify times. Where the key is the name of rest server and the value is
   * last modify time(in milliseconds).
   *
   * @return a map of modified times(in milliseconds).
   */
  public Map<String, Long> getLastModifyTimes() {
    return lastModifyTimes;
  }

  /**
   * Set the map of last modify times. Where the key is the name of rest server and the value is
   * last modify time(in milliseconds).
   *
   * @param lastModifyTimes a map of modified times(in milliseconds).
   */
  public void setLastModifyTimes(Map<String, Long> lastModifyTimes) {
    this.lastModifyTimes = lastModifyTimes;
  }
}
