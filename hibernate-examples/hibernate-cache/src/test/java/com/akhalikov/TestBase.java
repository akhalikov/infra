package com.akhalikov;

import com.akhalikov.core.BootstrapSessionFactoryBean;
import com.akhalikov.util.PropertiesUtils;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import static org.testng.Assert.assertEquals;

import javax.sql.DataSource;
import java.util.Properties;

abstract class TestBase {
  static DataSource dataSource = createDataSource();
  static Properties hibernateProperties = PropertiesUtils.fromFile("hibernate.properties");

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

  static Statistics createStatistics(SessionFactory sessionFactory) {
    Statistics statistics = sessionFactory.getStatistics();
    statistics.setStatisticsEnabled(true);
    statistics.clear();
    return statistics;
  }

  static LocalSessionFactoryBean createBootstrapSessionFactoryBean() {
    LocalSessionFactoryBean sessionFactoryBean = new BootstrapSessionFactoryBean();
    sessionFactoryBean.setDataSource(dataSource);
    sessionFactoryBean.setHibernateProperties(hibernateProperties);
    return sessionFactoryBean;
  }

  static void assertStatistics(Statistics statistics, int puts, int hits, int misses) {
    assertEquals(statistics.getSecondLevelCachePutCount(), puts, "puts");
    assertEquals(statistics.getSecondLevelCacheHitCount(), hits, "hits");
    assertEquals(statistics.getSecondLevelCacheMissCount(), misses, "misses");
  }
}
