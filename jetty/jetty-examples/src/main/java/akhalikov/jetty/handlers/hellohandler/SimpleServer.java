package akhalikov.jetty.handlers.hellohandler;

import org.eclipse.jetty.server.Server;

/**
 * Simple server example
 * https://eclipse.org/jetty/documentation/current/embedding-jetty.html
 */
class SimpleServer {
  public static void main(String[] args) throws Exception {
    Server server = new Server(8080);
    server.setHandler(new HelloHandler());

    server.start();
    server.join();
  }
}
