package xyz.aguang.rest.registry.entities;

import java.io.Serializable;

/**
 * This class represents a rest server.
 *
 * @author Grey King
 */
public class RestServer implements Serializable {

  private String name;

  private String zone;

  private String host;

  private int port;

  private long startupTime;

  /**
   * Get the name of rest server.
   *
   * @return name.
   */
  public String getName() {
    return name;
  }

  /**
   * Set the name of rest server.
   *
   * @param name The name of rest server.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Get the zone of rest server.
   *
   * @return zone.
   */
  public String getZone() {
    return zone;
  }

  /**
   * Set the zone of rest server.
   *
   * @param zone The zone of rest server.
   */
  public void setZone(String zone) {
    this.zone = zone;
  }

  /**
   * Get the host of rest server.
   *
   * @return host.
   */
  public String getHost() {
    return host;
  }

  /**
   * Set the host of rest server.
   *
   * @param host The host of rest server.
   */
  public void setHost(String host) {
    this.host = host;
  }

  /**
   * Get the port of rest server.
   *
   * @return port.
   */
  public int getPort() {
    return port;
  }

  /**
   * Set the port of reset server.
   *
   * @param port The port of reset server.
   */
  public void setPort(int port) {
    this.port = port;
  }

  /**
   * Get the startup time (in milliseconds) of reset server.
   *
   * @return startup time.
   */
  public long getStartupTime() {
    return startupTime;
  }

  /**
   * Set the startup time (in milliseconds) of rest server.
   *
   * @param startupTime startup time.
   */
  public void setStartupTime(long startupTime) {
    this.startupTime = startupTime;
  }
}
