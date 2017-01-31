package com.akhalikov;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.testng.annotations.BeforeMethod;

import javax.sql.DataSource;
import java.util.Properties;

abstract class TestBase {
  static DataSource dataSource = createDataSource();

  void addTestData() {
    final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    jdbcTemplate.execute("INSERT INTO event (title, event_date) VALUES ('Java Meetup', '2016-11-18 19:00:00')");
    jdbcTemplate.execute("INSERT INTO event (title, event_date) VALUES ('Python Meetup', '2016-11-20 18:00:00')");
    jdbcTemplate.execute("INSERT INTO event (title, event_date) VALUES ('Go Meetup', '2016-11-20 18:30:00')");
    jdbcTemplate.execute("INSERT INTO event (title, event_date) VALUES ('Hackathon', '2016-11-20 19:30:00')");
  }

  private static DataSource createDataSource() {
    SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

    dataSource.setDriverClass(org.hsqldb.jdbc.JDBCDriver.class);
    dataSource.setUrl("jdbc:hsqldb:mem");

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
}
