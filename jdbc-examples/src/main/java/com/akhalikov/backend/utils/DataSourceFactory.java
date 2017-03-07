package com.akhalikov.backend.utils;

import org.postgresql.ds.PGSimpleDataSource;

import java.util.Properties;

public class DataSourceFactory {

  public static PGSimpleDataSource createPGSimpleDataSource(Properties properties) {
    return createPGSimpleDataSource(
        properties.getProperty("jdbc.url"),
        properties.getProperty("jdbc.user"),
        properties.getProperty("jdbc.password")
    );
  }

  public static PGSimpleDataSource createPGSimpleDataSource(String url, String user, String password) {
    PGSimpleDataSource dataSource = new PGSimpleDataSource();
    dataSource.setUrl(url);
    dataSource.setUser(user);
    dataSource.setPassword(password);
    return dataSource;
  }

  private DataSourceFactory() {
  }
}
