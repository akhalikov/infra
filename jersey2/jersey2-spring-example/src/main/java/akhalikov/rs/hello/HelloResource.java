package akhalikov.rs.hello;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@Singleton
@Path("/rs")
@Named
public class HelloResource {

  private final HelloService helloService;

  @Inject
  public HelloResource(HelloService helloService) {
    this.helloService = helloService;
  }

  @GET
  @Path("/hello")
  public String hello(@DefaultValue("world") @QueryParam("name") String name) {
    return helloService.getGreeting(name);
  }
}
