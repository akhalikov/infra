package akhalikov.jersey2.helloworld;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/")
public class HelloWorldResource {
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  @GET
  public String hello() {
    log.info("Test log message");

    return "<h3>Example Jersey web app</h3>";
  }
}
