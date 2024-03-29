package xyz.aguang.rest.registry.controllers;

import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.aguang.rest.registry.services.RestRegistryService;
import xyz.aguang.rest.requests.RestRegistryHoldRequest;
import xyz.aguang.rest.responses.RestRegistryHoldResponse;

@RestController
public class RestRegistryController {

  @Autowired private RestRegistryService registryService;

  @PostMapping("/rest/registry/hold")
  public CompletableFuture<RestRegistryHoldResponse> hold(RestRegistryHoldRequest request) {

    return registryService.hold(request);
  }
}
