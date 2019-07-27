package xyz.aguang.rest.requests;

import java.io.Serializable;
import java.util.Map;
import xyz.aguang.rest.entities.RestServer;

/**
 * Hold request of rest registry.
 *
 * @author Grey King
 */
public class RestRegistryHoldRequest implements Serializable {

  private RestServer from;

  private boolean active;

  private Map<String, Long> previousModifyTimes;

  private long watchTimeout;

  /**
   * Get the client-side rest server of the hold request.
   *
   * @return client-side rest server.
   */
  public RestServer getFrom() {
    return from;
  }

  /**
   * Set the client-side rest server of the hold request.
   *
   * @param from client-side rest server.
   */
  public void setFrom(RestServer from) {
    this.from = from;
  }

  /**
   * Get the active status of client-side rest server.
   *
   * @return active status.
   */
  public boolean isActive() {
    return active;
  }

  /**
   * Set the active status of client-side rest server.
   *
   * @param active active status.
   */
  public void setActive(boolean active) {
    this.active = active;
  }

  /**
   * Get a map of previous modify times, where the key is rest servers' name and the value is the
   * last modify time currently storing in client-side rest server.
   *
   * @return a map of previous modify times.
   */
  public Map<String, Long> getPreviousModifyTimes() {
    return previousModifyTimes;
  }

  /**
   * Set a map of previous modify times, where the key is rest servers' name and the value is the
   * last modify time currently storing in client-side rest server.
   *
   * @param previousModifyTimes a map of previous modify times.
   */
  public void setPreviousModifyTimes(Map<String, Long> previousModifyTimes) {
    this.previousModifyTimes = previousModifyTimes;
  }

  /**
   * Get the watch timeout. The hold request will be blocked by the rest registry server until any
   * of the requesting rest servers' instances changed after the given modify timestamp or blocking
   * time up to the given timeout.
   *
   * <p>While the requesting rest servers are the rest servers of which the names exist in the key
   * set of field previousModifyTimes.
   *
   * <p>The watch timeout is in milliseconds.
   *
   * @return watch timeout of hold request.
   */
  public long getWatchTimeout() {
    return watchTimeout;
  }

  /**
   * Set the watch timeout. The hold request will be blocked by the rest registry server until any
   * of the requesting rest servers' instances changed after the given modify timestamp or blocking
   * time up to the given timeout.
   *
   * <p>While the requesting rest servers are the rest servers of which the names exist in the key
   * set of field previousModifyTimes.
   *
   * <p>The watch timeout is in milliseconds.
   *
   * @param watchTimeout watch timeout of hold request.
   */
  public void setWatchTimeout(long watchTimeout) {
    this.watchTimeout = watchTimeout;
  }
}
