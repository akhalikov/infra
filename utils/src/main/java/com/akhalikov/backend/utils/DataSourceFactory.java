package com.akhalikov.backend.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DataSourceFactory {

  public static ComboPooledDataSource createC3P0DataSource(Properties properties) throws Exception {

    ComboPooledDataSource ds = new ComboPooledDataSource();
    ds.setJdbcUrl(properties.getProperty("jdbc.url"));
    ds.setUser(properties.getProperty("jdbc.user"));
    ds.setPassword(properties.getProperty("jdbc.password"));

    if (properties.getProperty("jdbc.driverClass") != null) {
      ds.setDriverClass(properties.getProperty("jdbc.driverClass"));
    }

    ds.setMinPoolSize(1);
    ds.setMaxPoolSize(5);
    ds.setAcquireIncrement(1);
    ds.setMaxConnectionAge(7200);
    ds.setMaxIdleTime(1800);
    ds.setMaxIdleTimeExcessConnections(120);
    ds.setIdleConnectionTestPeriod(180);
    ds.setTestConnectionOnCheckin(true);
    ds.setAcquireRetryAttempts(5);
    ds.setAcquireRetryDelay(1);
    ds.setUnreturnedConnectionTimeout(300);
    ds.setDebugUnreturnedConnectionStackTraces(true);
    ds.setMaxStatements(0);
    ds.setMaxStatementsPerConnection(50);
    ds.setCheckoutTimeout(5000);
    checkDataSource(ds);
    return ds;
  }

  public static PGSimpleDataSource createPGSimpleDataSource(Properties properties) {
    return createPGSimpleDataSource(
        properties.getProperty("url"),
        properties.getProperty("username"),
        properties.getProperty("password")
    );
  }

  private static PGSimpleDataSource createPGSimpleDataSource(String url, String user, String password) {
    PGSimpleDataSource ds = new PGSimpleDataSource();
    ds.setUrl(url);
    ds.setUser(user);
    ds.setPassword(password);
    checkDataSource(ds);
    return ds;
  }

  public static DataSource createHsqlDataSource() {
    SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

    dataSource.setDriverClass(org.hsqldb.jdbc.JDBCDriver.class);
    dataSource.setUrl("proxy:hsqldb:mem");

    final Properties properties = new Properties();
    properties.setProperty("user", "sa");
    properties.setProperty("password", "");
    properties.setProperty("sql.syntax_pgs", "true");
    properties.setProperty("sql.enforce_tdc_delete", "false");
    properties.setProperty("sql.enforce_tdc_update", "false");
    properties.setProperty("sql.enforce_refs", "true");
    properties.setProperty("sql.avg_scale", "10");
    dataSource.setConnectionProperties(properties);

    return dataSource;
  }

  private static void checkDataSource(DataSource dataSource) {
    Connection connection = null;
    try {
      connection = dataSource.getConnection();
      if (!connection.isValid(1000)) {
        throw new SQLException("Bad connection");
      }
    } catch (SQLException e) {
      throw new RuntimeException("failed to check connection", e);
    } finally {
      if (connection != null) {
        try {
          connection.close();
        } catch (SQLException e) {
        }
      }
    }
  }

  private DataSourceFactory() {
  }
}
