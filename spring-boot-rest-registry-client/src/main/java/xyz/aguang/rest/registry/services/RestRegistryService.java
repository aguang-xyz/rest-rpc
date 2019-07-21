package xyz.aguang.rest.registry.services;

import java.util.List;
import xyz.aguang.rest.registry.entities.RestServer;

public interface RestRegistryService {

  List<RestServer> providersOf(String name);
}
