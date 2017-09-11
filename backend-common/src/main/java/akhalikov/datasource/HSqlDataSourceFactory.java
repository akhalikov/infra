package akhalikov.datasource;

import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.util.Properties;

public class HSqlDataSourceFactory {

  public static DataSource createHsqlDataSource() {
    SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

    dataSource.setDriverClass(org.hsqldb.jdbc.JDBCDriver.class);
    dataSource.setUrl("proxy:hsqldb:mem");

    Properties properties = new Properties();
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

  private HSqlDataSourceFactory() {
  }
}
