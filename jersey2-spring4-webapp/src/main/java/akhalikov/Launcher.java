package akhalikov;

import akhalikov.server.jetty.JettyFactory;
import akhalikov.utils.properties.Settings;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.spi.spring.container.SpringComponentProviderFactory;
import com.sun.jersey.spi.spring.container.servlet.SpringServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.ThreadPool;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class Launcher {
  public static void main(String[] args) {
    SLF4JBridgeHandler.removeHandlersForRootLogger();
    SLF4JBridgeHandler.install();

    Class<?>[] configClasses = {ProdConfig.class};
    try (final AbstractApplicationContext context = new AnnotationConfigApplicationContext(configClasses)) {
      Settings settings = context.getBean(Settings.class);
      ThreadPool threadPool = JettyFactory.createJettyThreadPool(4, 4);
      Server server = JettyFactory.createJettyServer(settings, threadPool);

      ResourceConfig resourceConfig = createJerseyResourceConfig(context);
      SpringComponentProviderFactory componentProviderFactory = new SpringComponentProviderFactory(resourceConfig, context);

      ServletContextHandler handler = new ServletContextHandler();
      handler.setContextPath("/");



      ServletHolder servletHolder = new ServletHolder();
      servletHolder.setServlet(server);
    }
  }

  private static ResourceConfig createJerseyResourceConfig(ListableBeanFactory beanFactory) {
    JerseyResourceConfigBuilder configBuilder = new JerseyResourceConfigBuilder();
    beanFactory.getBeansWithAnnotation(javax.ws.rs.Path.class).values()
        .forEach(configBuilder::addResource);
    return configBuilder.createResourceConfig();
  }
}
