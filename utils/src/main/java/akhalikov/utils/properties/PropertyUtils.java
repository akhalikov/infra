package akhalikov.utils.properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class PropertyUtils {
  private static final Logger LOGGER = LoggerFactory.getLogger(PropertyUtils.class);

  public static Properties load() {
    Properties defaultProperties = fromFile("app.properties");
    Properties overriddenProperties = fromFile("overridden.properties");
    defaultProperties.putAll(overriddenProperties);
    return defaultProperties;
  }

  public static Properties fromFile(String fileName) {
    ClassLoader classLoader = PropertyUtils.class.getClassLoader();
    Properties properties = new Properties();
    try (InputStream inputStream = classLoader.getResourceAsStream(fileName)) {
      if (inputStream == null) {
        LOGGER.warn("could not find log file {}", fileName);
      } else {
        properties.load(inputStream);
      }
      return properties;
    } catch (IOException e) {
      throw new RuntimeException("failed to load properties from file " + fileName, e);
    }
  }

  public static Properties fromFileInSettingsDir(String fileName) throws Exception {
    final String settingsDir = System.getProperty("settingsDir");
    final Properties properties = new Properties();
    final Path defaultPath = Paths.get(settingsDir, fileName);
    try (InputStream inputStream = Files.newInputStream(defaultPath)) {
      properties.load(inputStream);
    }
    return properties;
  }

  public static void setSystemPropertyIfAbsent(final String name, final String value) {
    if (System.getProperty(name) == null) {
      System.setProperty(name, value);
    }
  }

  private PropertyUtils() {
  }
}
