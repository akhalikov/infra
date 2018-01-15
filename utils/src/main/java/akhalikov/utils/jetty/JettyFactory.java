package akhalikov.utils.jetty;

import akhalikov.utils.properties.Settings;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ThreadPool;

public class JettyFactory {

  public static ThreadPool createJettyThreadPool(Settings settings) {
    return createJettyThreadPool(
      settings.getInteger("maxThreads"),
      settings.getInteger("minThreads"));
  }

  public static ThreadPool createJettyThreadPool(int maxThreads, int minThreads) {
    return new QueuedThreadPool(maxThreads, minThreads);
  }

  public static Server createJettyServer(Settings settings) {
    ThreadPool threadPool = createJettyThreadPool(settings);
    return createJettyServer(settings, threadPool);
  }

  public static Server createJettyServer(Settings settings, ThreadPool threadPool) {
    final Server server = new Server(threadPool);
    final ServerConnector serverConnector = new ServerConnector(
        server,
        settings.getInteger("acceptors"),
        settings.getInteger("selectors"),
        createHttpConnectionFactory());

    serverConnector.setPort(settings.getInteger("port"));
    serverConnector.setIdleTimeout(settings.getInteger("connectionIdleTimeoutMs"));
    serverConnector.setAcceptQueueSize(settings.getInteger("acceptQueueSize"));
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
}

