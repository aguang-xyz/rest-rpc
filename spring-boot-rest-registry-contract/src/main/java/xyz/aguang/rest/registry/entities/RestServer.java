package xyz.aguang.rest.registry.entities;

import java.io.Serializable;

public class RestServer implements Serializable {

  private String name;

  private String zone;

  private String address;

  private int port;

  private long startupTime;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getZone() {
    return zone;
  }

  public void setZone(String zone) {
    this.zone = zone;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public long getStartupTime() {
    return startupTime;
  }

  public void setStartupTime(long startupTime) {
    this.startupTime = startupTime;
  }
}
