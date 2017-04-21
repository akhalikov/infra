package com.akhalikov.backend.jetty.simpleserver;

import org.eclipse.jetty.server.Server;

/**
 * Simple server example
 * https://eclipse.org/jetty/documentation/current/embedding-jetty.html
 */
class SimpleServer {

  public static void main(String[] args) throws Exception {
    // Create a basic Jetty server object that will listen on port 8080.  Note that if you set this to port 0
    // then a randomly available port will be assigned that you can either look in the logs for the port,
    // or programmatically obtain it for use in test cases.
    Server server = new Server(8080);
    server.dumpStdErr();

    // Start things up! By using the server.join() the server thread will join with the current thread.
    // See "http://docs.oracle.com/javase/1.5.0/docs/api/java/lang/Thread.html#join()" for more details.
    server.start();
    server.join();
  }
}
