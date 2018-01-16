package akhalikov.utils.hsql;

import com.mchange.v2.c3p0.DriverManagerDataSource;
import javax.sql.DataSource;
import java.util.Properties;

public class EmbeddedHSqlDataSourceFactory {

  public static DataSource createHsqlDataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setJdbcUrl("proxy:hsqldb:mem");
    dataSource.setProperties(getDataSourceProperties());
    return dataSource;
  }

  private static Properties getDataSourceProperties() {
    Properties properties = new Properties();
    properties.setProperty("user", "sa");
    properties.setProperty("password", "");
    properties.setProperty("sql.syntax_pgs", "true");
    properties.setProperty("sql.enforce_tdc_delete", "false");
    properties.setProperty("sql.enforce_tdc_update", "false");
    properties.setProperty("sql.enforce_refs", "true");
    properties.setProperty("sql.avg_scale", "10");
    return properties;
  }

  private EmbeddedHSqlDataSourceFactory() {
  }
}
