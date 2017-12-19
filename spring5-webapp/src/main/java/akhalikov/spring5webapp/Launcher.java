package akhalikov.spring5webapp;

import akhalikov.server.jetty.JettyFactory;
import akhalikov.utils.properties.Settings;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.thread.ThreadPool;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class Launcher {

  public static void main(String[] args) throws Exception {
    SLF4JBridgeHandler.removeHandlersForRootLogger();
    SLF4JBridgeHandler.install();

    try (final AbstractApplicationContext context = new AnnotationConfigApplicationContext()) {
      ThreadPool threadPool = JettyFactory.createJettyThreadPool(8, 2);
      Settings settings = context.getBean(Settings.class);
      Server server = JettyFactory.createJettyServer(settings.getSubSettings("jetty"), threadPool);

      WebappServletInitializer servletInitializer = new WebappServletInitializer();

      server.setStopAtShutdown(true);
      server.setStopTimeout(10);

      server.start();
      server.join();
    }
  }
}
