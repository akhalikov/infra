package simpleservice.status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Status controller
 */
@Path("/status")
public class StatusResource {

  private final Logger log = LoggerFactory.getLogger(StatusResource.class);

  @GET
  @Produces("text/xml")
  public String getStatus() {
    log.info("getStatus");
    return "OK";
  }
}
