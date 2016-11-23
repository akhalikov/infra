package hellologback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;

@Named("HelloService")
public class HelloService {
  private final Logger log = LoggerFactory.getLogger(getClass());

  public void doSomethig() {
    log.info("performing some business logic");
  }
}
