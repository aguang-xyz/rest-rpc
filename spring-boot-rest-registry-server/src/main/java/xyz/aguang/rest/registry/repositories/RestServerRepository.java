package xyz.aguang.rest.registry.repositories;

import java.util.concurrent.CompletableFuture;
import xyz.aguang.rest.registry.entities.RestServer;
import xyz.aguang.rest.registry.entities.RestServerList;

public interface RestServerRepository {

  CompletableFuture<Void> putIfAbsent(RestServer server);

  CompletableFuture<Void> remove(RestServer server);

  RestServerList get(String name);

  CompletableFuture<RestServerList> get(String name, long previousModifyTime);
}
