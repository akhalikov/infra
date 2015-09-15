package lecture1.app;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * @author ahalikov
 */
public class App {
    public static void main(String[] args) throws Exception {
        Frontend frontend = new Frontend();

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(frontend), "/authform");

        Server server = new Server(8080);
        server.setHandler(context);

        server.start();
        server.join();
    }
}
