package com.akhalikov.util;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils {

  public static Properties fromFile(String fileName) {
    try {
      Properties properties = new Properties();
      InputStream inputStream = new ClassPathResource(fileName).getInputStream();
      properties.load(inputStream);
      return properties;
    } catch (IOException e) {
      System.out.println("Could not load properties: " + fileName);
      throw new RuntimeException(e);
    }
  }
}
