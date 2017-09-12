package akhalikov.jdbc.jetty.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Simple servlet
 */
public class HelloServlet extends HttpServlet {

  private final Logger log = Logger.getLogger(HelloServlet.class.getName());

  @Override
  protected void doGet(HttpServletRequest req,
                       HttpServletResponse resp) throws ServletException, IOException {


    System.out.println("HelloServlet: requestUrl=" + req.getRequestURL().toString() + " from=" + req.getRemoteHost());

    resp.setContentType("text/html");
    resp.setStatus(HttpServletResponse.SC_OK);
    resp.getWriter().println("<h1>Hello!</h1>");
  }
}
