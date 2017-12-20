package akhalikov.spring4webapp;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import static akhalikov.utils.properties.PropertyUtils.setSystemPropertyIfAbsent;

public class Launcher {

  public static void main(String[] args) throws Exception {
    SLF4JBridgeHandler.removeHandlersForRootLogger();
    SLF4JBridgeHandler.install();

    setSystemPropertyIfAbsent("settingsDir", "src/etc/webapp");

    //Settings settings = graph.getBean(Settings.class);

    AnnotationConfigWebApplicationContext webAppContext = new AnnotationConfigWebApplicationContext();
    webAppContext.register(ProdAppConfig.class);

    Server server = new Server(8001);
    ServletContextHandler contextHandler = createServletContextHandler(webAppContext);

    server.setHandler(contextHandler);

    server.start();
    server.join();
  }

  public static ServletContextHandler createServletContextHandler(WebApplicationContext context) {
    ServletContextHandler contextHandler = new ServletContextHandler();
    contextHandler.setContextPath("/");

    DispatcherServlet dispatcherServlet = new DispatcherServlet(context);
    ServletHolder servletHolder = new ServletHolder("mainServlet", dispatcherServlet);
    contextHandler.addServlet(servletHolder, "/");
    contextHandler.addEventListener(new ContextLoaderListener(context));

    return contextHandler;
  }
}
