package com.akhalikov.backend.jetty;

import org.eclipse.jetty.server.Server;

/**
 * Convenient class for Jetty server
 */
public abstract class AbstractJettyServer {
  protected Server server;

  public AbstractJettyServer() {
    server = new Server(8080);
  }

  protected abstract void configure() throws Exception;

  public void start() throws InterruptedException {
    try {
      configure();
      server.start();

    } catch (Exception e) {
      System.err.println("Failed to start, shutting down: " + e.getMessage());
      System.exit(1);
    }
    server.join();
  }
}
