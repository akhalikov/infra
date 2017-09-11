package akhalikov.server;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import javax.servlet.Servlet;

public class ServerFactory {

  public static Server createServer(int port, Object...resources) {
    Servlet servlet = createServlet(resources);
    Handler requestsHandler = createHandler(servlet);
    Server server = new Server(port);
    server.setHandler(requestsHandler);
    return server;
  }

  private static Servlet createServlet(Object... resources) {
    ResourceConfig jerseyResourceConfig = new ResourceConfig();
    for (Object resource : resources) {
      jerseyResourceConfig.register(resource);
    }
    return new ServletContainer(jerseyResourceConfig);
  }

  private static Handler createHandler(Servlet mainServlet) {
    ServletHolder servletHolder = new ServletHolder("MainServlet", mainServlet);
    ServletContextHandler servletContextHandler = new ServletContextHandler();
    servletContextHandler.addServlet(servletHolder, "/*");
    return servletContextHandler;
  }

  private ServerFactory() {
  }
}
