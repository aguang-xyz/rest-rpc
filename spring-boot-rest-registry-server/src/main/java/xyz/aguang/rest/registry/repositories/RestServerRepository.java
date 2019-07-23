package xyz.aguang.rest.registry.repositories;

import xyz.aguang.rest.registry.entities.RestServer;
import xyz.aguang.rest.registry.entities.RestServerList;

import java.util.concurrent.CompletableFuture;

public interface RestServerRepository {

  CompletableFuture<Void> putIfAbsent(RestServer server);

  CompletableFuture<Void> remove(RestServer server);

  RestServerList get(String name);

  CompletableFuture<RestServerList> get(String name, long previousModifyTime);
}
