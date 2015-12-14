package jettyserver.contexts;

import jettyserver.AbstractJettyWebapp;
import jettyserver.servlets.HelloServlet;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;

/**
 * Embedding ServletContexts
 *
 * A ServletContextHandler is a specialization of ContextHandler with support for standard sessions and Servlets.
 * The following OneServletContext example instantiates a DefaultServlet to server static content from /tmp/
 * and a HelloServlet.
 */
public class OneServletContext extends AbstractJettyWebapp {
  @Override
  protected void configure() {
    ServletContextHandler context =
      new ServletContextHandler(ServletContextHandler.SESSIONS);
    context.setContextPath("/");
    context.setResourceBase(System.getProperty("java.io.tmpdir"));
    server.setHandler(context);

    context.addServlet(HelloServlet.class, "/hello/*");
    context.addServlet(DefaultServlet.class, "/");
  }

  public static void main(String[] args) {
    new OneServletContext().start();
  }
}
