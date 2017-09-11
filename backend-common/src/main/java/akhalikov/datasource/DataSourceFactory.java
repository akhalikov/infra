package akhalikov.datasource;

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
    PGSimpleDataSource dataSource = new PGSimpleDataSource();
    dataSource.setUrl(properties.getProperty("url"));
    dataSource.setUser(properties.getProperty("username"));
    dataSource.setPassword(properties.getProperty("password"));
    checkDataSource(dataSource);
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
