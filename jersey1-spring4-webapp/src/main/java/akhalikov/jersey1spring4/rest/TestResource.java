package akhalikov.jersey1spring4.rest;

import com.sun.jersey.spi.resource.Singleton;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@Named
@Singleton
@Path("/")
public class TestResource {
  private final String serviceName;

  public TestResource(String serviceName) {
    this.serviceName = serviceName;
  }

  @GET
  @Path("/hello")
  public String hello(@DefaultValue("world") @QueryParam("name") String name) {
    return String.format("Hello, %s! from %s", name, serviceName);
  }
}
