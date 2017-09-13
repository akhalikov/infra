package akhalikov.utils.properties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Settings {
  private final Properties properties;

  public Settings(Properties properties) {
    this.properties = properties;
  }

  public String getString(String key) {
    return properties.getProperty(key);
  }

  public Integer getInteger(String key) {
    String value = getString(key);
    return (value != null) ? Integer.parseInt(value): null;
  }

  public Long getLong(final String key) {
    String value = getString(key);
    return (value != null) ? Long.parseLong(value): null;
  }

  public Boolean getBoolean(String key) {
    String value = getString(key);
    return (value != null) ? Boolean.parseBoolean(value): null;
  }

  public Properties getSubProperties(String prefix) {
    Properties subProperties = new Properties();
    properties.stringPropertyNames().stream()
        .filter(key -> key.startsWith(prefix + '.'))
        .forEach(key -> subProperties.put(key.substring(prefix.length() + 1), properties.getProperty(key)));
    return subProperties;
  }

  public Settings getSubSettings(String prefix) {
    return new Settings(getSubProperties(prefix));
  }

  public List<String> getStringList(String key) {
    String value = getString(key);
    return (value != null)
        ? Arrays.asList(value.split("[,\\s]+"))
        : new ArrayList<>();
  }
}
