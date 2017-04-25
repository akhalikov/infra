package com.akhalikov.backend.jetty;

import com.akhalikov.backend.jetty.config.AppConfig;
import org.eclipse.jetty.server.Server;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class WebappMain {

  public static void main(String[] args) throws Exception {
    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

    Server server = applicationContext.getBean(Server.class, "jettyServer");
    server.join();
  }
}
