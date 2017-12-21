package akhalikov;

import akhalikov.server.jetty.JettyFactory;
import akhalikov.utils.properties.Settings;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.ThreadPool;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import static akhalikov.utils.properties.PropertyUtils.setSystemPropertyIfAbsent;

public class Spring4JerseyLauncher {

  public static void main(String[] args) throws Exception {
    SLF4JBridgeHandler.removeHandlersForRootLogger();
    SLF4JBridgeHandler.install();

    setSystemPropertyIfAbsent("settingsDir", "src/etc/webapp");

    Class<?>[] configClasses = {ProdConfig.class};
    try (final AbstractApplicationContext context = new AnnotationConfigApplicationContext(configClasses)) {
      Settings settings = context.getBean(Settings.class);

      ResourceConfig resourceConfig = new ResourceConfig();
      resourceConfig.register(TestResource.class);

      ServletHolder servletHolder = new ServletHolder(new ServletContainer(resourceConfig));

      ServletContextHandler contextHandler = new ServletContextHandler();
      contextHandler.setContextPath("/");
      contextHandler.addServlet(servletHolder, "/");

      contextHandler.addEventListener(new ContextLoaderListener());
      contextHandler.setInitParameter("contextClass", AnnotationConfigWebApplicationContext.class.getName());
      contextHandler.setInitParameter("contextConfigLocation", ProdConfig.class.getName());

      ThreadPool threadPool = JettyFactory.createJettyThreadPool(settings.getSubSettings("jetty"));
      Server server = JettyFactory.createJettyServer(settings.getSubSettings("jetty"), threadPool);

      server.setHandler(contextHandler);

      server.start();
      server.join();
    }
  }
}
