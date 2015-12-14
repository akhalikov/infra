package jettyserver;

import org.eclipse.jetty.server.Server;

/**
 * Convenient class for Jetty server
 */
public abstract class AbstractJettyServer {
  public static final int DEFAULT_PORT = 8080;

  protected Server server;
  private int port;

  public AbstractJettyServer() {
    this(DEFAULT_PORT);
  }

  public AbstractJettyServer(int port) {
    this.port = port;
    this.server = new Server(this.port);
    configure();
  }

  protected abstract void configure();

  public void start() {
    try {
      server.start();
      server.join();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
