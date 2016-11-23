package hellolog4j;

import org.apache.log4j.Logger;

import javax.inject.Named;

@Named("DemoService")
public class DemoService {
  private final Logger log = Logger.getLogger(getClass());

  public void doSomethig() {
    log.info("performing some business logic");
  }
}
