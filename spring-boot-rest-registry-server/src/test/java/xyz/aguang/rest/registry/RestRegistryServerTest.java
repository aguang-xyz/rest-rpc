package xyz.aguang.rest.registry;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = RestRegistryServer.class)
public class RestRegistryServerTest {

  @Test
  public void testNothing() {}

  @Test
  public void failedTest() {

    throw new RuntimeException("ahhh");
  }
}
