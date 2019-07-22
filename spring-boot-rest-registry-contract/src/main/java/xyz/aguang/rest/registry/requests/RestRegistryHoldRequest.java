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

  public Set<String> getWatchingNames() {
    return watchingNames;
  }

  public void setWatchingNames(Set<String> watchingNames) {
    this.watchingNames = watchingNames;
  }

  public long getPreviousModifyTime() {
    return previousModifyTime;
  }

  public void setPreviousModifyTime(long previousModifyTime) {
    this.previousModifyTime = previousModifyTime;
  }

  public long getWatchTimeout() {
    return watchTimeout;
  }

  public void setWatchTimeout(long watchTimeout) {
    this.watchTimeout = watchTimeout;
  }
}
