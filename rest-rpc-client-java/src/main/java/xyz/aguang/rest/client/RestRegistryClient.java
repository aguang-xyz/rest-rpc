package xyz.aguang.rest.client;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import xyz.aguang.rest.entities.RestServer;

public interface RestRegistryClient {

  CompletableFuture<Set<RestServer>> get(String name);

  void awaitTermination(long timeout) throws InterruptedException;
}
