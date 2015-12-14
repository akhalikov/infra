package jerseywebapp;

import org.eclipse.jetty.server.*;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * Webapp main class
 *
 * @author ahalikov
 */
public class Main {
  public static void main(String[] args) {
    final Server server = new Server();

    server.setHandler(createWebAppContextHandler("src/main/webapp"));
    server.addConnector(createServerConnector(server));

    try {
      server.start();
      server.join();
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      server.destroy();
    }
  }

  private static Handler createWebAppContextHandler(final String resourceBase) {
    final WebAppContext context = new WebAppContext();
    context.setContextPath("/");
    context.setResourceBase(resourceBase);
    context.setParentLoaderPriority(true);
    return context;
  }

  private static HttpConfiguration createHttpConfiguration() {
    HttpConfiguration httpConfig = new HttpConfiguration();
    httpConfig.setSecurePort(8443);
    httpConfig.setSendServerVersion(true);
    httpConfig.setSendDateHeader(true);
    return httpConfig;
  }

  private static ServerConnector createServerConnector(final Server server) {
    ServerConnector connector = new ServerConnector(server,
      new HttpConnectionFactory(createHttpConfiguration()));
    connector.setPort(9090);
    connector.setIdleTimeout(30000);
    return connector;
  }
}
