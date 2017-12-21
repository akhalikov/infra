package akhalikov.spring4webinitializer;

import org.eclipse.jetty.server.Server;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import static akhalikov.utils.properties.PropertyUtils.setSystemPropertyIfAbsent;

public class Launcher {

  public static void main(String[] args) throws Exception {
    SLF4JBridgeHandler.removeHandlersForRootLogger();
    SLF4JBridgeHandler.install();

    setSystemPropertyIfAbsent("settingsDir", "src/etc/webapp");

    Server server = new Server(9001);

    server.start();
    server.join();
  }

  private static WebApplicationContext createWebApplicationContext(Class<?> ...configClasses) {
    AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
    rootContext.register(configClasses);
    rootContext.refresh();
    return rootContext;
  }
}
