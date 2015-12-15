package jettyserver.servlets;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

/**
 * The MinimalServlets example creates a ServletHandler instance and configures a single HelloServlet.
 *
 * Created by artur on 14.12.15.
 */
public class MinimalServlets {

  public static void main(String[] args) throws Exception {
    Server server = new Server(8080);

    // The ServletHandler is a dead simple way to create a context handler
    // that is backed by an instance of a Servlet.
    // This handler then needs to be registered with the Server object.
    ServletHandler handler = new ServletHandler();
    server.setHandler(handler);

    // Passing in the class for the Servlet allows jetty to instantiate an
    // instance of that Servlet and mount it on a given context path.

    // IMPORTANT:
    // This is a raw Servlet, not a Servlet that has been configured
    // through a web.xml @WebServlet annotation, or anything similar.
    handler.addServletWithMapping(HelloServlet.class, "/*");
    handler.addServletWithMapping(ByeServlet.class, "/bye");

    server.start();
    server.join();
  }
}
