package xyz.aguang.rest.registry.services;

import xyz.aguang.rest.registry.entities.RestServer;

import java.util.List;

public interface RestRegistryService {

  List<RestServer> providersOf(String name);
}
