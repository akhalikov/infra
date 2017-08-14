package com.akhalikov;

import com.akhalikov.backend.utils.datasource.DataSourceFactory;
import com.akhalikov.backend.utils.properties.PrefixedProperties;
import com.akhalikov.backend.utils.properties.PropertiesFactory;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import javax.sql.DataSource;
import java.util.Properties;

abstract class TestBase {
  static final Properties hibernateProperties = PropertiesFactory.fromFile("hibernate.properties");

  static final DataSource dataSource = DataSourceFactory.createPGSimpleDataSource(
    new PrefixedProperties(hibernateProperties, "hibernate.connection."));

  private static final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

  private static Statistics statistics;

  @BeforeMethod
  public void setUp() throws Exception {
    addTestData();
  }

  @AfterMethod
  public void tearDownClass() throws Exception {
    clearTestData();
  }

  private static void addTestData() {
    System.out.println("adding test data...");
    jdbcTemplate.execute("INSERT INTO events (title, event_date) VALUES ('Java Meetup', '2016-11-18 19:00:00')");
    jdbcTemplate.execute("INSERT INTO events (title, event_date) VALUES ('Python Meetup', '2016-11-20 18:00:00')");
    jdbcTemplate.execute("INSERT INTO events (title, event_date) VALUES ('Go Meetup', '2016-11-20 18:30:00')");
    jdbcTemplate.execute("INSERT INTO events (title, event_date) VALUES ('Hackathon', '2016-11-20 19:30:00')");
  }

  private static void clearTestData() {
    System.out.println("clean up test data...");
    jdbcTemplate.execute("DELETE FROM events");
    jdbcTemplate.execute("ALTER SEQUENCE events_event_id_seq RESTART WITH 1");
  }

  static Statistics createStatistics(SessionFactory sessionFactory) {
    Statistics statistics = sessionFactory.getStatistics();
    statistics.setStatisticsEnabled(true);
    statistics.clear();
    return statistics;
  }

  static LocalSessionFactoryBean createSessionFactoryBean() {
    LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
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
