package akhalikov;

import akhalikov.config.JerseyConfig;
import akhalikov.config.ProdConfig;
import akhalikov.server.jetty.JettyFactory;
import akhalikov.utils.properties.Settings;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlet.ServletMapping;
import org.eclipse.jetty.util.thread.ThreadPool;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import javax.servlet.Servlet;

import static akhalikov.utils.properties.PropertyUtils.setSystemPropertyIfAbsent;

public class AppMain {

  public static void main(String[] args) throws Exception {
    SLF4JBridgeHandler.removeHandlersForRootLogger();
    SLF4JBridgeHandler.install();

    setSystemPropertyIfAbsent("settingsDir", "src/etc/webapp");

    Class<?>[] configClasses = {ProdConfig.class};
    try (final AbstractApplicationContext context = new AnnotationConfigApplicationContext(configClasses)) {
      Settings settings = context.getBean(Settings.class);

      JerseyConfig jerseyConfig = new JerseyConfig();
      jerseyConfig.property("contextConfig", context);
      Servlet jerseyServlet = new ServletContainer(jerseyConfig);

      Handler requestsHandler = createJettyRequestHandler(jerseyServlet);

      ThreadPool threadPool = JettyFactory.createJettyThreadPool(settings.getSubSettings("jetty"));
      Server server = JettyFactory.createJettyServer(settings.getSubSettings("jetty"), threadPool);
      server.setHandler(requestsHandler);

      server.start();
      server.join();
    }
  }

  private static Handler createJettyRequestHandler(Servlet mainServlet) {
    ServletHolder servletHolder = new ServletHolder("mainServlet", mainServlet);
    servletHolder.setAsyncSupported(true);

    ServletMapping servletMapping = new ServletMapping();
    servletMapping.setServletName("mainServlet");
    servletMapping.setPathSpec("/*");

    final ServletHandler servletHandler = new ServletHandler();
    servletHandler.addServlet(servletHolder);
    servletHandler.addServletMapping(servletMapping);

    final ServletContextHandler servletContextHandler = new ServletContextHandler();
    servletContextHandler.setServletHandler(servletHandler);
    return servletContextHandler;
  }
}
