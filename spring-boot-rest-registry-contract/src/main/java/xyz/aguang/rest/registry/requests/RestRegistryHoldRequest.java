package xyz.aguang.rest.registry.requests;

import xyz.aguang.rest.registry.entities.RestServer;

import java.io.Serializable;
import java.util.Map;

public class RestRegistryHoldRequest implements Serializable {

  private RestServer from;

  private boolean active;

  private Map<String, Long> previousModifyTimes;

  private long watchTimeout;

  public RestServer getFrom() {
    return from;
  }

  public void setFrom(RestServer from) {
    this.from = from;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public Map<String, Long> getPreviousModifyTimes() {
    return previousModifyTimes;
  }

  public void setPreviousModifyTimes(Map<String, Long> previousModifyTimes) {
    this.previousModifyTimes = previousModifyTimes;
  }

  public long getWatchTimeout() {
    return watchTimeout;
  }

  public void setWatchTimeout(long watchTimeout) {
    this.watchTimeout = watchTimeout;
  }
}
