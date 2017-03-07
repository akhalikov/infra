package com.akhalikov.backend.utils;

import org.postgresql.ds.PGSimpleDataSource;

import java.util.Objects;
import java.util.Properties;

import static java.util.Objects.requireNonNull;

public class PGSimpleDataSourceFactory {

  public static PGSimpleDataSource createPGSimpleDataSource(Properties properties) {
    return createPGSimpleDataSource(
        properties.getProperty("jdbc.url"),
        properties.getProperty("jdbc.user"),
        properties.getProperty("jdbc.password")
    );
  }

  static PGSimpleDataSource createPGSimpleDataSource(String url, String user, String password) {
    PGSimpleDataSource dataSource = new PGSimpleDataSource();
    dataSource.setUrl(url);
    dataSource.setUser(user);
    dataSource.setPassword(password);
    return dataSource;
  }

  private PGSimpleDataSourceFactory() {
  }
}
