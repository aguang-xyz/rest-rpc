package xyz.aguang.rest.registry.repositories.impl;

import org.springframework.stereotype.Repository;
import xyz.aguang.rest.registry.entities.RestServer;
import xyz.aguang.rest.registry.entities.RestServerList;
import xyz.aguang.rest.registry.repositories.RestServerRepository;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@Repository
public class RestServerRepositoryImpl implements RestServerRepository {

  private Map<String, Set<Consumer<RestServerList>>> modifyCallbackMap = new HashMap<>();
  private Map<String, RestServerList> serverListMap = new ConcurrentHashMap<>();

  private Set<Consumer<RestServerList>> getModifyCallbacks(String name) {

    return modifyCallbackMap.computeIfAbsent(name, k -> new HashSet<>());
  }

  private void triggerModifyCallbacks(RestServerList serverList) {

    getModifyCallbacks(serverList.getName()).forEach(consumer -> consumer.accept(serverList));
  }

  private void addModifyCallback(String name, Consumer<RestServerList> callback) {

    getModifyCallbacks(name).add(callback);
  }

  private void doPutIfAbsent(RestServer server) {

    final RestServerList serverList = get(server.getName());

    synchronized (serverList) {
      if (serverList.getServers().contains(server)) {

        return;
      }

      if (serverList.getLastModifyTime() == System.currentTimeMillis()) {

        try {

          Thread.sleep(1L);
        } catch (InterruptedException e) {

          throw new RuntimeException(e);
        }
      }

      serverList.getServers().add(server);
      serverList.setLastModifyTime(System.currentTimeMillis());

      triggerModifyCallbacks(serverList);
    }
  }

  @Override
  public CompletableFuture<Void> putIfAbsent(RestServer server) {

    Objects.requireNonNull(server);

    return CompletableFuture.runAsync(() -> doPutIfAbsent(server));
  }

  private void doRemove(RestServer server) {

    final RestServerList serverList = get(server.getName());

    synchronized (serverList) {
      if (!serverList.getServers().contains(server)) {

        return;
      }

      if (serverList.getLastModifyTime() == System.currentTimeMillis()) {

        try {

          Thread.sleep(1L);
        } catch (InterruptedException e) {

          throw new RuntimeException(e);
        }
      }

      serverList.getServers().remove(server);
      serverList.setLastModifyTime(System.currentTimeMillis());

      triggerModifyCallbacks(serverList);
    }
  }

  @Override
  public CompletableFuture<Void> remove(RestServer server) {

    Objects.requireNonNull(server);

    return CompletableFuture.runAsync(() -> doRemove(server));
  }

  @Override
  public RestServerList get(String name) {

    return serverListMap.computeIfAbsent(name, RestServerList::create);
  }

  @Override
  public CompletableFuture<RestServerList> get(String name, long previousModifyTime) {

    final RestServerList serverList = get(name);

    /**
     * This is an optimization to reduce mutex locks. If the last modify time of server list is
     * already later then previous modify time. The current server list can be returned directly.
     */
    if (serverList.getLastModifyTime() > previousModifyTime) {

      return CompletableFuture.completedFuture(serverList);
    }

    synchronized (serverList) {
      if (serverList.getLastModifyTime() > previousModifyTime) {

        return CompletableFuture.completedFuture(serverList);
      } else {

        CompletableFuture<RestServerList> future = new CompletableFuture<>();

        addModifyCallback(name, newServerList -> future.complete(newServerList));

        return future;
      }
    }
  }
}
