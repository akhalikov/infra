package akhalikov.jetty;

import akhalikov.jetty.config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class WebappMain {
  public static void main(String[] args) {
    new AnnotationConfigApplicationContext(AppConfig.class);
  }
}
