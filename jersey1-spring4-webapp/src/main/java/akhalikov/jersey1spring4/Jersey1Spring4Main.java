package akhalikov.jersey1spring4;

import akhalikov.utils.properties.PropertyUtils;
import akhalikov.utils.properties.Settings;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.webapp.WebAppContext;

import java.util.Properties;

public class Jersey1Spring4Main {

  public static void main(String[] args) throws Exception {

    PropertyUtils.setSystemPropertyIfAbsent("settingsDir", "src/etc");

    Properties properties = PropertyUtils.fromFileInSettingsDir("app.properties");
    Settings settings = new Settings(properties);

    Server server = new Server(settings.getInteger("port"));

    WebAppContext webAppContext = new WebAppContext();
    webAppContext.setContextPath("/");
    webAppContext.setResourceBase("src/main/webapp");
    webAppContext.setClassLoader(Thread.currentThread().getContextClassLoader());

    server.setHandler(webAppContext);
    server.start();
  }
}
