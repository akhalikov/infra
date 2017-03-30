package com.akhalikov;

import com.akhalikov.backend.utils.DataSourceFactory;
import com.akhalikov.backend.utils.PrefixedProperties;
import com.akhalikov.backend.utils.PropertiesFactory;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import static org.testng.Assert.assertEquals;

import javax.sql.DataSource;
import java.util.Properties;

abstract class TestBase {
  static final Properties hibernateProperties = PropertiesFactory.fromFile("hibernate.properties");
  static final DataSource dataSource = DataSourceFactory.createPGSimpleDataSource(
    new PrefixedProperties(hibernateProperties, "hibernate.connection"));

  void addTestData() {
    final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    jdbcTemplate.execute("INSERT INTO users VALUES (1, 'Mark', 'Anderson'");
    jdbcTemplate.execute("INSERT INTO users VALUES (2, 'Adam', 'Smith')");

    //jdbcTemplate.execute("INSERT INTO users VALUES ('Go Meetup', '2016-11-20 18:30:00')");
    //jdbcTemplate.execute("INSERT INTO users VALUES ('Hackathon', '2016-11-20 19:30:00')");
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
