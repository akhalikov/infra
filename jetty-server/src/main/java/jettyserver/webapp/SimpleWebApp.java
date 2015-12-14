package jettyserver.webapp;

import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * Configuring web application as POJO
 */
public class SimpleWebApp {
  public static void main(String[] args) throws Exception {
    doMain();
  }

  private static void doMain() throws Exception {
    Server server = new Server(8080);

    ServerConnector connector = new ServerConnector(server, 2, -1,
      new HttpConnectionFactory(getHttpConfig()));
    connector.setPort(8080);
    connector.setIdleTimeout(30000);
    server.addConnector(connector);

    server.setStopAtShutdown(true);
    server.setStopTimeout(5000);

    WebAppContext webapp = new WebAppContext();
    webapp.setContextPath("/");
    webapp.setResourceBase("src/main/webapp");
    webapp.setDescriptor("target/web.xml");

    server.setHandler(webapp);

    server.start();
    server.join();
  }

  private static HttpConfiguration getHttpConfig() {
    HttpConfiguration httpConfig = new HttpConfiguration();
    // below settings should be moved to properties file
    httpConfig.setSecurePort(8443);
    httpConfig.setOutputBufferSize(65536);
    httpConfig.setRequestHeaderSize(16384);
    httpConfig.setResponseHeaderSize(65536);
    httpConfig.setSendServerVersion(true);
    httpConfig.setSendDateHeader(true);
    httpConfig.setHeaderCacheSize(512);
    return httpConfig;
  }
}
