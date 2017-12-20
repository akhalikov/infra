package akhalikov.spring4webinitializer;

import akhalikov.server.jetty.JettyFactory;
import akhalikov.spring4webapp.ProdAppConfig;
import akhalikov.utils.properties.Settings;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import static akhalikov.spring4webapp.Launcher.createServletContextHandler;
import static akhalikov.utils.properties.PropertyUtils.setSystemPropertyIfAbsent;

public class Launcher {

  public static void main(String[] args) throws Exception {
    SLF4JBridgeHandler.removeHandlersForRootLogger();
    SLF4JBridgeHandler.install();

    setSystemPropertyIfAbsent("settingsDir", "src/etc/webapp");

    try (AnnotationConfigWebApplicationContext rootContext = createWebApplicationContext(ProdAppConfig.class)) {

      Settings settings = rootContext.getBean(Settings.class);
      Server server = JettyFactory.createJettyServer(settings.getSubSettings("jetty"));

      ServletContextHandler contextHandler = createServletContextHandler(rootContext);
      server.setHandler(contextHandler);

      server.start();
      server.join();
    }
  }

  private static WebApplicationContext createWebApplicationContext(Class<?> ...configClasses) {
    AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
    rootContext.register(configClasses);
    rootContext.refresh();
    return rootContext;
  }
}
