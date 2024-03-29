package xyz.aguang.rest.registry.services.impl;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.aguang.rest.entities.RestServer;
import xyz.aguang.rest.registry.entities.RestServerList;
import xyz.aguang.rest.registry.repositories.RestServerRepository;
import xyz.aguang.rest.registry.services.RestRegistryService;
import xyz.aguang.rest.requests.RestRegistryHoldRequest;
import xyz.aguang.rest.responses.RestRegistryHoldResponse;

@Service
public class RestRegistryServiceImpl implements RestRegistryService {

  @Autowired private RestServerRepository serverRepository;

  private CompletableFuture<Void> doProcessActive(RestServer server, boolean active) {

    return active ? serverRepository.putIfAbsent(server) : serverRepository.remove(server);
  }

  private CompletableFuture<Object> doProcessWatch(
      Map<String, Long> previousModifyTime, long timeout) {

    return CompletableFuture.anyOf(
            previousModifyTime
                .keySet()
                .parallelStream()
                .map(
                    watchingName ->
                        serverRepository.get(watchingName, previousModifyTime.get(watchingName)))
                .toArray(CompletableFuture[]::new))
        .completeOnTimeout(new Object(), timeout, TimeUnit.MILLISECONDS);
  }

  private RestRegistryHoldResponse buildHoldResponse(Map<String, Long> previousModifyTimes) {

    Set<RestServerList> serverLists =
        previousModifyTimes
            .keySet()
            .parallelStream()
            .map(watchingName -> serverRepository.get(watchingName))
            .collect(Collectors.toSet());

    return RestRegistryHoldResponse.create(
        serverLists
            .parallelStream()
            .collect(Collectors.toMap(RestServerList::getName, RestServerList::getServers)),
        serverLists
            .parallelStream()
            .collect(Collectors.toMap(RestServerList::getName, RestServerList::getLastModifyTime)));
  }

  @Override
  public CompletableFuture<RestRegistryHoldResponse> hold(RestRegistryHoldRequest request) {

    return CompletableFuture.allOf(
            doProcessActive(request.getFrom(), request.isActive()),
            doProcessWatch(request.getPreviousModifyTimes(), request.getWatchTimeout()))
        .thenApply(x -> buildHoldResponse(request.getPreviousModifyTimes()));
  }
}
