package xyz.aguang.rest.registry.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class RestServerList implements Serializable {

  private String name;

  private Set<RestServer> servers;

  private long lastModifyTime;

  public static RestServerList create(String name) {

    RestServerList serverList = new RestServerList();

    serverList.setName(name);
    serverList.setServers(new HashSet<>());
    serverList.setLastModifyTime(0L);

    return serverList;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<RestServer> getServers() {
    return servers;
  }

  public void setServers(Set<RestServer> servers) {
    this.servers = servers;
  }

  public long getLastModifyTime() {
    return lastModifyTime;
  }

  public void setLastModifyTime(long lastModifyTime) {
    this.lastModifyTime = lastModifyTime;
  }
}
