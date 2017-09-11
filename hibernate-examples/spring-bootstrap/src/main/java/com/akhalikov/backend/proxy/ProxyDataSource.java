package com.akhalikov.backend.proxy;

import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

public class ProxyDataSource implements DataSource {
  private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ProxyDataSource.class);

  private final DataSource delegate;
  private final String dataSourceName = "master";

  public ProxyDataSource(DataSource delegate) {
    this.delegate = delegate;
  }

  @Override
  public Connection getConnection() throws SQLException {
    Connection connection = delegate.getConnection();
    LOGGER.info("Got connection: {}, readOnly={}", connection, connection.isReadOnly());
    return new ProxyConnection(connection, dataSourceName);
  }

  @Override
  public Connection getConnection(String username, String password) throws SQLException {
    Connection connection = delegate.getConnection(username, password);
    LOGGER.info("Got connection: {}, readOnly={}", connection, connection.isReadOnly());
    return new ProxyConnection(connection, dataSourceName);
  }

  @Override
  public <T> T unwrap(Class<T> iface) throws SQLException {
    return delegate.unwrap(iface);
  }

  @Override
  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    return delegate.isWrapperFor(iface);
  }

  @Override
  public PrintWriter getLogWriter() throws SQLException {
    return delegate.getLogWriter();
  }

  @Override
  public void setLogWriter(PrintWriter out) throws SQLException {
    delegate.setLogWriter(out);
  }

  @Override
  public void setLoginTimeout(int seconds) throws SQLException {
    delegate.setLoginTimeout(seconds);
  }

  @Override
  public int getLoginTimeout() throws SQLException {
    return delegate.getLoginTimeout();
  }

  @Override
  public Logger getParentLogger() throws SQLFeatureNotSupportedException {
    return delegate.getParentLogger();
  }
}
