package com.akhalikov.backend.utils.properties;

import java.util.Enumeration;
import java.util.Properties;

public class PrefixedProperties extends Properties {

  @SuppressWarnings("unchecked")
  public PrefixedProperties(Properties properties, String prefix){
    if (properties == null) {
      return;
    }
    Enumeration<String> en = (Enumeration<String>) properties.propertyNames();
    while (en.hasMoreElements()) {
      String propName = en.nextElement();
      String propValue = properties.getProperty(propName);

      if (propName.startsWith(prefix)){
        String key = propName.substring(prefix.length());
        setProperty(key, propValue);
      }
    }
  }
}
