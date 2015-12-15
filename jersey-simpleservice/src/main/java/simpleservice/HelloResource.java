package simpleservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * Hello resource
 */
@Path("/")
public class HelloResource {

  private final Logger log = LoggerFactory.getLogger(this.getClass());

  @GET
  public String hello() {
    log.info("Test log message");
    return "<h1>Hello!</h1>";
  }
}
