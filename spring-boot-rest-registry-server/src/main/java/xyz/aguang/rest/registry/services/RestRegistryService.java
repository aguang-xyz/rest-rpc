package xyz.aguang.rest.registry.services;

import java.util.concurrent.CompletableFuture;
import xyz.aguang.rest.registry.requests.RestRegistryHoldRequest;
import xyz.aguang.rest.registry.responses.RestRegistryHoldResponse;

public interface RestRegistryService {

  CompletableFuture<RestRegistryHoldResponse> hold(RestRegistryHoldRequest request);
}
