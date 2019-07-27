package xyz.aguang.rest.client.impl;

import com.alibaba.fastjson.JSON;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.aguang.rest.client.RestRegistryClient;
import xyz.aguang.rest.entities.HostAndPort;
import xyz.aguang.rest.entities.RestServer;
import xyz.aguang.rest.requests.RestRegistryHoldRequest;
import xyz.aguang.rest.responses.RestRegistryHoldResponse;

public class RestSingleRegistryClient implements RestRegistryClient {

  private static final Logger LOGGER = LoggerFactory.getLogger(RestSingleRegistryClient.class);

  private HttpClient httpClient = HttpClient.newHttpClient();

  private Map<String, Set<RestServer>> cachedServers = new ConcurrentHashMap<>();

  private Map<String, Long> cachedModifyTime = new ConcurrentHashMap<>();

  private Map<String, Set<Consumer<Set<RestServer>>>> holdCallback = new ConcurrentHashMap<>();

  private RestServer self;
  private String registryAddress;
  private int registryPort;
  private long watchTimeout;

  private ExecutorService executorService = Executors.newSingleThreadExecutor();

  public RestSingleRegistryClient(RestServer self, HostAndPort registry, long watchTimeout) {

    validateSelfParameter(self);

    this.self = self;

    Objects.requireNonNull(registry.getHost());

    this.registryAddress = registry.getHost();

    this.registryPort = registry.getPort();

    this.watchTimeout = watchTimeout;

    this.executorService.submit(() -> this.holdLoop());
  }

  private void validateSelfParameter(RestServer self) {

    Objects.requireNonNull(self);

    Objects.requireNonNull(self.getName());
    Objects.requireNonNull(self.getHost());
    Objects.requireNonNull(self.getPort());
    Objects.requireNonNull(self.getZone());
    Objects.requireNonNull(self.getStartupTime());
  }

  private RestRegistryHoldRequest buildHoldAliveRequest() {

    RestRegistryHoldRequest request = new RestRegistryHoldRequest();

    request.setFrom(this.self);

    request.setActive(true);

    synchronized (this) {
      request.setPreviousModifyTimes(new HashMap<>(this.cachedModifyTime));
    }

    request.setWatchTimeout(this.watchTimeout);

    return request;
  }

  private RestRegistryHoldRequest buildHoldUnaliveRequest() {

    RestRegistryHoldRequest request = new RestRegistryHoldRequest();

    request.setFrom(this.self);

    request.setActive(false);

    request.setPreviousModifyTimes(new HashMap<>());

    request.setWatchTimeout(0L);

    return request;
  }

  private Set<RestServer> getCachedServers(String name) {

    return cachedServers.computeIfAbsent(name, (k) -> new HashSet<>());
  }

  private Long getCachedModifyTime(String name) {

    return cachedModifyTime.computeIfAbsent(name, (k) -> 0L);
  }

  private Set<Consumer<Set<RestServer>>> getHolCallbacks(String name) {

    return holdCallback.computeIfAbsent(name, (k) -> new HashSet<>());
  }

  private RestRegistryHoldResponse doHoldRequest(RestRegistryHoldRequest request)
      throws IOException, InterruptedException {

    return JSON.parseObject(
        httpClient
            .send(
                HttpRequest.newBuilder()
                    .uri(
                        URI.create(
                            "http://"
                                + this.registryAddress
                                + ":"
                                + this.registryPort
                                + "/rest/registry/hold"))
                    .POST(HttpRequest.BodyPublishers.ofString(JSON.toJSONString(request)))
                    .build(),
                HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8))
            .body(),
        RestRegistryHoldResponse.class);
  }

  private void holdLoop() {

    RestRegistryHoldRequest request = null;

    while (Thread.currentThread().isAlive()) {

      try {

        request = buildHoldAliveRequest();

        RestRegistryHoldResponse response = doHoldRequest(request);

        LOGGER.info(
            "doHoldRequest, request = {}, response = {}",
            JSON.toJSONString(request),
            JSON.toJSONString(response));

        synchronized (this) {
          response
              .getLastModifyTimes()
              .forEach(
                  (name, modifyTime) -> {
                    cachedModifyTime.put(name, modifyTime);

                    Set<RestServer> modifiedServers =
                        response.getModifiedServers().computeIfAbsent(name, (k) -> new HashSet<>());

                    cachedServers.put(name, modifiedServers);

                    Set<Consumer<Set<RestServer>>> callbacks = getHolCallbacks(name);

                    callbacks.forEach(callback -> callback.accept(modifiedServers));

                    callbacks.clear();
                  });
        }

      } catch (IOException | InterruptedException e) {

        LOGGER.warn("doHoldRequest, request = {}, exception = {}", JSON.toJSONString(request), e);
      } catch (Throwable t) {

        LOGGER.error("doHoldRequest, request = {}, exception = {}", JSON.toJSONString(request), t);
      }
    }

    try {

      doHoldRequest(buildHoldUnaliveRequest());

    } catch (IOException | InterruptedException e) {

      LOGGER.warn("doHoldRequest, request = {}, exception = {}", JSON.toJSONString(request), e);
    }
  }

  @Override
  public CompletableFuture<Set<RestServer>> get(String name) {

    if (getCachedModifyTime(name) > 0L) {

      return CompletableFuture.completedFuture(getCachedServers(name));
    }

    CompletableFuture<Set<RestServer>> future = new CompletableFuture<>();

    synchronized (this) {
      getHolCallbacks(name).add((modifiedRestServers) -> future.complete(modifiedRestServers));
    }

    return future;
  }

  @Override
  public void awaitTermination(long timeout) throws InterruptedException {

    this.executorService.awaitTermination(timeout, TimeUnit.MILLISECONDS);
  }
}
