package akhalikov.config;

import akhalikov.rs.hello.HelloResource;
import org.glassfish.jersey.server.ResourceConfig;

public class JerseyConfig extends ResourceConfig {
  public JerseyConfig() {
    register(HelloResource.class);
  }
}
