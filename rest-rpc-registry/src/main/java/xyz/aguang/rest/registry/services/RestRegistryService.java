package xyz.aguang.rest.registry.services;

import java.util.concurrent.CompletableFuture;
import xyz.aguang.rest.requests.RestRegistryHoldRequest;
import xyz.aguang.rest.responses.RestRegistryHoldResponse;

public interface RestRegistryService {

  CompletableFuture<RestRegistryHoldResponse> hold(RestRegistryHoldRequest request);
}
