package akhalikov.server.jetty;

import akhalikov.utils.properties.Settings;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ThreadPool;

public class JettyFactory {
  private static ThreadPool jettyThreadPool;

  public static Server createJettyServer(Settings jettySettings) {
    jettyThreadPool = new QueuedThreadPool(
        jettySettings.getInteger("maxThreads"),
        jettySettings.getInteger("minThreads"));

    return createJettyServer(jettySettings, jettyThreadPool);
  }

  public static Server createJettyServer(Settings jettySettings, ThreadPool threadPool) {
    jettyThreadPool = threadPool;

    final Server server = new Server(jettyThreadPool);
    final ServerConnector serverConnector = new ServerConnector(
        server,
        jettySettings.getInteger("acceptors"),
        jettySettings.getInteger("selectors"),
        createHttpConnectionFactory());

    serverConnector.setPort(jettySettings.getInteger("port"));
    serverConnector.setIdleTimeout(jettySettings.getInteger("connectionIdleTimeoutMs"));
    serverConnector.setAcceptQueueSize(jettySettings.getInteger("acceptQueueSize"));
    server.addConnector(serverConnector);

    server.setStopAtShutdown(true);

    return server;

  }

  private static HttpConnectionFactory createHttpConnectionFactory() {
    final HttpConfiguration httpConfiguration = new HttpConfiguration();
    httpConfiguration.setSecurePort(8443);
    httpConfiguration.setOutputBufferSize(65536);
    httpConfiguration.setRequestHeaderSize(16384);
    httpConfiguration.setResponseHeaderSize(65536);
    httpConfiguration.setSendServerVersion(false);
    httpConfiguration.setBlockingTimeout(5000);
    return new HttpConnectionFactory(httpConfiguration);
  }

  public static ThreadPool getJettyThreadPool() {
    return jettyThreadPool;
  }
}
