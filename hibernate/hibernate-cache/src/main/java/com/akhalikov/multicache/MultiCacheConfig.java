package com.akhalikov.multicache;

import java.util.Properties;

class MultiCacheConfig {
  private static final String PREFIX = "hibernate.multicache";
  private static final String PROVIDERS = PREFIX + ".providers";
  private static final String REGION_FILTER = PREFIX + ".%s.region.filter";
  private static final String REGION_FACTORY_CLASS = PREFIX + "%s.region.factory_class";

  private final Properties properties;

  MultiCacheConfig(Properties properties) {
    this.properties = properties;
  }

  String[] getProviders() {
    return properties.getProperty(PROVIDERS).split(",");
  }

  String getRegionFactoryClass(String provider) {
    return properties.getProperty(String.format(REGION_FACTORY_CLASS, provider));
  }

  String getRegionFilter(String provider) {
    return properties.getProperty(String.format(REGION_FILTER, provider));
  }
}
