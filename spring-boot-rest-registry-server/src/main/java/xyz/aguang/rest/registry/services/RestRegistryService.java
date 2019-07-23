package xyz.aguang.rest.registry.services;

import xyz.aguang.rest.registry.requests.RestRegistryHoldRequest;
import xyz.aguang.rest.registry.responses.RestRegistryHoldResponse;

import java.util.concurrent.CompletableFuture;

public interface RestRegistryService {

  CompletableFuture<RestRegistryHoldResponse> hold(RestRegistryHoldRequest request);
}
