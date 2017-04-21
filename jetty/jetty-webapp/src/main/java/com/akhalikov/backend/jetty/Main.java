package com.akhalikov.backend.jetty;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class Main {

  public static void main(String[] args) throws Exception {
    Server server = new Server(8080);

    server.setStopAtShutdown(true);
    server.setStopTimeout(5000);

    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
    context.setContextPath("/");
    server.setHandler(context);

    context.addServlet(DefaultServlet.class, "/");

    server.start();
    server.join();
  }
}
