package hellolog4j;

import hellolog4j.config.AppConfig;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
  private static final Logger LOGGER = Logger.getLogger(App.class);

  public static void main(String[] args) {
    LOGGER.info("Starting ...");
    AnnotationConfigApplicationContext ctx
      = new AnnotationConfigApplicationContext(AppConfig.class);
    DemoService service = (DemoService) ctx.getBean("DemoService");
    service.doSomethig();
    ctx.registerShutdownHook();
  }
}
