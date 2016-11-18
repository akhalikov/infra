package com.akhalikov.backend.hibernate5;

import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;

import javax.inject.Inject;

public abstract class BaseTest extends AbstractTestNGSpringContextTests {
  @Inject
  protected JdbcTemplate jdbcTemplate;

  @Inject
  protected SessionFactory sessionFactory;

  @BeforeClass
  public void setUpBase() throws Exception {
    jdbcTemplate.execute("INSERT INTO event (title, event_date) VALUES ('Java Meetup', '2016-11-18 19:00:00')");
    jdbcTemplate.execute("INSERT INTO event (title, event_date) VALUES ('Python Meetup', '2016-11-20 18:00:00')");
  }
}
