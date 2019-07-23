package xyz.aguang.rest.registry.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.aguang.rest.registry.requests.RestRegistryHoldRequest;
import xyz.aguang.rest.registry.responses.RestRegistryHoldResponse;
import xyz.aguang.rest.registry.services.RestRegistryService;

import java.util.concurrent.CompletableFuture;

@RestController
public class RestRegistryController {

  @Autowired private RestRegistryService registryService;

  @PostMapping("/rest/registry/hold")
  public CompletableFuture<RestRegistryHoldResponse> hold(RestRegistryHoldRequest request) {

    return registryService.hold(request);
  }
}
