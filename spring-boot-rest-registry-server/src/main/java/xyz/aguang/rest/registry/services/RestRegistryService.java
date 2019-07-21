package xyz.aguang.rest.registry.services;

import java.util.concurrent.CompletableFuture;
import xyz.aguang.rest.registry.requests.RestHoldRequest;
import xyz.aguang.rest.registry.responses.RestHoldResponse;

public interface RestRegistryService {

  CompletableFuture<RestHoldResponse> hold(RestHoldRequest request);
}
