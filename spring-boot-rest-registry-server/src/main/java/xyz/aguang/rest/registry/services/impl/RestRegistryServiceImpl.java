package xyz.aguang.rest.registry.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.aguang.rest.registry.entities.RestServer;
import xyz.aguang.rest.registry.entities.RestServerList;
import xyz.aguang.rest.registry.repositories.RestServerRepository;
import xyz.aguang.rest.registry.requests.RestRegistryHoldRequest;
import xyz.aguang.rest.registry.responses.RestRegistryHoldResponse;
import xyz.aguang.rest.registry.services.RestRegistryService;

import java.util.Comparator;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class RestRegistryServiceImpl implements RestRegistryService {

  @Autowired private RestServerRepository serverRepository;

  private CompletableFuture<Void> doProcessActive(RestServer server, boolean active) {

    return active ? serverRepository.putIfAbsent(server) : serverRepository.remove(server);
  }

  private CompletableFuture<Object> doProcessWatch(
      Set<String> watchingNames, long previousModifyTime, long timeout) {

    return CompletableFuture.anyOf(
            watchingNames
                .parallelStream()
                .map(watchingName -> serverRepository.get(watchingName, previousModifyTime))
                .toArray(CompletableFuture[]::new))
        .completeOnTimeout(new Object(), timeout, TimeUnit.MILLISECONDS);
  }

  private RestRegistryHoldResponse buildHoldResponse(
      Set<String> watchingNames, long previousModifyTime) {

    Set<RestServerList> serverLists =
        watchingNames
            .parallelStream()
            .map(watchingName -> serverRepository.get(watchingName))
            .collect(Collectors.toSet());

    return RestRegistryHoldResponse.create(
        serverLists
            .parallelStream()
            .collect(Collectors.toMap(RestServerList::getName, RestServerList::getServers)),
        serverLists
            .parallelStream()
            .map(RestServerList::getLastModifyTime)
            .max(Comparator.comparing(x -> x))
            .orElse(0L));
  }

  @Override
  public CompletableFuture<RestRegistryHoldResponse> hold(RestRegistryHoldRequest request) {

    return CompletableFuture.allOf(
            doProcessActive(request.getFrom(), request.isActive()),
            doProcessWatch(
                request.getWatchingNames(),
                request.getPreviousModifyTime(),
                request.getWatchTimeout()))
        .thenApply(
            x -> buildHoldResponse(request.getWatchingNames(), request.getPreviousModifyTime()));
  }
}
