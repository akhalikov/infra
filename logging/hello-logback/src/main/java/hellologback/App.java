package hellologback;

import hellologback.config.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
  private static final Logger logger = LoggerFactory.getLogger(App.class);

  public static void main(String[] args) {
    logger.info("Starting application...");
    AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
    HelloService service = (HelloService) ctx.getBean("HelloService");
    service.doSomethig();
    ctx.registerShutdownHook();
  }
}
