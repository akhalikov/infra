package com.akhalikov;

import com.akhalikov.event.Event;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import javax.inject.Inject;

public abstract class SessionFactoryTestBase extends AbstractTestNGSpringContextTests {
  @Inject
  private JdbcTemplate jdbcTemplate;

  @Inject
  protected SessionFactory sessionFactory;

  Statistics statistics;

  @BeforeClass
  public void setUpBase() throws Exception {
    jdbcTemplate.execute("INSERT INTO event (title, event_date) VALUES ('Java Meetup', '2016-11-18 19:00:00')");
    jdbcTemplate.execute("INSERT INTO event (title, event_date) VALUES ('Python Meetup', '2016-11-20 18:00:00')");
  }

  @BeforeMethod
  public void setUp() throws Exception {
    statistics = sessionFactory.getStatistics();
    statistics.setStatisticsEnabled(true);
  }

  void getEventFromSessionTwice() {
    Session session = sessionFactory.openSession();
    session.get(Event.class, 1);
    session.close();

    session = sessionFactory.openSession();
    session.get(Event.class, 1);
    session.close();
  }
}
