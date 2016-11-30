package com.akhalikov;

import org.hibernate.Cache;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import javax.inject.Inject;

public abstract class TestBase extends AbstractTestNGSpringContextTests {
  @Inject
  protected JdbcTemplate jdbcTemplate;

  @Inject
  protected SessionFactory sessionFactory;

  Statistics statistics;
  Cache cache;

  @BeforeClass
  public void setUpBase() throws Exception {
    jdbcTemplate.execute("INSERT INTO event (title, event_date) VALUES ('Java Meetup', '2016-11-18 19:00:00')");
    jdbcTemplate.execute("INSERT INTO event (title, event_date) VALUES ('Python Meetup', '2016-11-20 18:00:00')");
    jdbcTemplate.execute("INSERT INTO event (title, event_date) VALUES ('Go Meetup', '2016-11-20 18:30:00')");
    jdbcTemplate.execute("INSERT INTO event (title, event_date) VALUES ('Hackathon', '2016-11-20 19:30:00')");

    statistics = sessionFactory.getStatistics();
    statistics.setStatisticsEnabled(true);
    statistics.clear();

    cache = sessionFactory.getCache();
    cache.evictAllRegions();
  }

  @AfterMethod
  public void tearDown() throws Exception {
    cache.evictAllRegions();
    statistics.clear();
  }
}
