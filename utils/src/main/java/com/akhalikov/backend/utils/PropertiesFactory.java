package com.akhalikov.backend.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesFactory {
  private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesFactory.class);

  public static Properties load() {
    Properties defaultProperties = fromFile("app.properties");

    Properties overriddenProperties = fromFile("overridden.properties");

    defaultProperties.putAll(overriddenProperties);

    return defaultProperties;
  }

  public static Properties fromFile(String fileName) {
    ClassLoader classLoader = PropertiesFactory.class.getClassLoader();
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

  private PropertiesFactory() {
  }
}
