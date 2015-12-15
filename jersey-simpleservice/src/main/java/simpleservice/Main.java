package simpleservice;

import org.eclipse.jetty.server.*;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlet.ServletMapping;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.bridge.SLF4JBridgeHandler;

import javax.servlet.Servlet;

/**
 * Webapp main class
 *
 * @author ahalikov
 */
public class Main {
  private static int SERVER_PORT = 8080;

  public static void main(String[] args) throws Exception {
    SLF4JBridgeHandler.removeHandlersForRootLogger();
    SLF4JBridgeHandler.install();

    Server jettyServer = null;
    try {
      final ResourceConfig jerseyResourceConfig = createJerseyResourceConfig();
      final Servlet jerseyServlet = new ServletContainer(jerseyResourceConfig);
      final Handler jettyRequestHandler = createJettyRequestHandler(jerseyServlet);
      jettyServer = createJettyServer(jettyRequestHandler);

      jettyServer.start();
      jettyServer.join();
    } finally {
      if (jettyServer != null)
        jettyServer.destroy();
    }
  }

  private static HttpConfiguration createHttpConfiguration() {
    HttpConfiguration httpConfig = new HttpConfiguration();
    httpConfig.setSecurePort(8443);
    httpConfig.setSendServerVersion(true);
    httpConfig.setSendDateHeader(true);
    return httpConfig;
  }

  private static ResourceConfig createJerseyResourceConfig() {
    final ResourceConfig resourceConfig = new ResourceConfig();
    resourceConfig.register(new HelloResource());
    return resourceConfig;
  }

  private static Handler createJettyRequestHandler(final Servlet mainServlet) {
    final ServletHolder servletHolder = new ServletHolder("mainServlet", mainServlet);

    final ServletMapping servletMapping = new ServletMapping();
    servletMapping.setServletName("mainServlet");
    servletMapping.setPathSpec("/*");

    final ServletHandler servletHandler = new ServletHandler();
    servletHandler.addServlet(servletHolder);
    servletHandler.addServletMapping(servletMapping);

    final ServletContextHandler servletContextHandler = new ServletContextHandler();
    servletContextHandler.setServletHandler(servletHandler);
    return servletContextHandler;
  }

  private static Server createJettyServer(final Handler requestsHandler) {
    final Server server = new Server();
    ServerConnector connector = new ServerConnector(server, new HttpConnectionFactory(createHttpConfiguration()));
    connector.setPort(SERVER_PORT);
    connector.setIdleTimeout(30000);
    server.addConnector(connector);
    server.setHandler(requestsHandler);
    return server;
  }
}
