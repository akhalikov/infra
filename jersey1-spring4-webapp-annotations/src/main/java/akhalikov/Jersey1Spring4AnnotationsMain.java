package akhalikov;

import akhalikov.utils.properties.PropertyUtils;
import akhalikov.utils.properties.Settings;
import org.eclipse.jetty.server.Server;

import java.util.Properties;

public class Jersey1Spring4AnnotationsMain {
  public static void main(String[] args) throws Exception {
    PropertyUtils.setSystemPropertyIfAbsent("settingsDir", "src/etc");

    Properties properties = PropertyUtils.fromFileInSettingsDir("app.properties");
    Settings settings = new Settings(properties);

    Server server = new Server(settings.getInteger("port"));

    server.setHandler(null);
    server.start();
  }
}
