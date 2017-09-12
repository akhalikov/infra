package akhalikov.jdbc.jetty.handlers.hellohandler;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * To produce a response to a request, Jetty requires that you set a Handler on the server.
 * A handler may:
 *  - Examine/modify the HTTP request.
 *  - Generate the complete HTTP response.
 *  - Call another Handler (see HandlerWrapper).
 *  - Select one or many Handlers to call (see HandlerCollection).
 */
public class HelloHandler extends AbstractHandler {

  final String greeting;
  final String body;

  public HelloHandler() {
    this("Hello handler");
  }

  public HelloHandler(String greeting) {
    this(greeting, null);
  }

  public HelloHandler(String greeting, String body) {
    this.greeting = greeting;
    this.body = body;
  }

  @Override
  public void handle(String target,
                     Request baseRequest,
                     HttpServletRequest request,
                     HttpServletResponse response) throws IOException, ServletException {

    response.setContentType("text/html; charset=utf-8");
    response.setStatus(HttpServletResponse.SC_OK);

    PrintWriter out = response.getWriter();
    out.println("<h1>" + greeting + "</h1>");
    if (body != null) {
      out.println(body);
    }
    baseRequest.setHandled(true);
  }
}
