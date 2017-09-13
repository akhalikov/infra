package akhalikov.server.jetty;

import org.eclipse.jetty.webapp.WebAppContext;

public class WebAppContextFactory {

  public static WebAppContext createWebAppContext() {
    final WebAppContext webApp = new WebAppContext();
    webApp.setContextPath("/");
    webApp.setResourceBase("src/main/webapp");
    webApp.setDescriptor("target/web.xml");
    webApp.setClassLoader(Thread.currentThread().getContextClassLoader());
    return webApp;
  }
}
