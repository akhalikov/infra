package com.akhalikov.backend.jetty.contexts;

import com.akhalikov.backend.jetty.AbstractJettyServer;
import com.akhalikov.backend.jetty.servlets.HelloServlet;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;

/**
 * Embedding ServletContexts
 *
 * A ServletContextHandler is a specialization of ContextHandler with support for standard sessions and Servlets.
 * The following OneServletContext example instantiates a DefaultServlet to server static content from /tmp/
 * and a HelloServlet.
 */
class OneServletContext extends AbstractJettyServer {

  @Override
  protected void configure() throws Exception {
    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
    context.setContextPath("/");
    context.setResourceBase(System.getProperty("java.io.tmpdir"));
    server.setHandler(context);

    context.addServlet(HelloServlet.class, "/hello/*");
    context.addServlet(DefaultServlet.class, "/");
  }

  public static void main(String[] args) throws Exception {
    new OneServletContext().start();
  }
}
