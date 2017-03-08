package com.akhalikov.backend.utils;

import com.mchange.v2.c3p0.C3P0Registry;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.postgresql.ds.PGSimpleDataSource;

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
    ds.setDriverClass(properties.getProperty("jdbc.driverClass"));
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

  private static void checkDataSource(DataSource dataSource) throws SQLException {
    Connection connection = dataSource.getConnection();
    if (!connection.isValid(1000)) {
      throw new SQLException("Bad connection");
    }
    connection.close();
  }

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

  private DataSourceFactory() {
  }
}
