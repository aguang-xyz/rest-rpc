package xyz.aguang.rest.client.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import xyz.aguang.rest.client.RestRegistryClient;
import xyz.aguang.rest.entities.HostAndPort;
import xyz.aguang.rest.entities.RestServer;

public class RestMultipleRegistryClient implements RestRegistryClient {

  private Set<RestRegistryClient> clients;

  public RestMultipleRegistryClient(
      RestServer self, Set<HostAndPort> registries, long watchTimeout) {

    this.clients =
        registries
            .stream()
            .map(registry -> new RestSingleRegistryClient(self, registry, watchTimeout))
            .collect(Collectors.toSet());
  }

  @Override
  public CompletableFuture<Set<RestServer>> get(String name) {

    Set<CompletableFuture<Set<RestServer>>> futures =
        clients.stream().map(client -> client.get(name)).collect(Collectors.toSet());

    return CompletableFuture.anyOf(futures.toArray(CompletableFuture[]::new))
        .thenApply(
            (k) -> {
              for (CompletableFuture<Set<RestServer>> future : futures) {

                if (future.isDone()) {

                  Set<RestServer> modifiedServers = future.getNow(null);

                  if (null != modifiedServers) {

                    return modifiedServers;
                  }
                }
              }

              return new HashSet<>();
            });
  }

  @Override
  public void awaitTermination(long timeout) throws InterruptedException {

    Set<InterruptedException> exceptions = new HashSet<>();

    CompletableFuture.allOf(
            clients
                .stream()
                .map(
                    client ->
                        CompletableFuture.runAsync(
                            () -> {
                              try {

                                client.awaitTermination(timeout);
                              } catch (InterruptedException e) {

                                exceptions.add(e);
                              }
                            }))
                .toArray(CompletableFuture[]::new))
        .join();

    if (!exceptions.isEmpty()) {

      throw new InterruptedException(
          exceptions.stream().map(e -> e.getMessage()).collect(Collectors.joining("; ")));
    }
  }
}
