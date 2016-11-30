package com.akhalikov;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

public class App {
  public static void main(String[] args) {
    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("memcached/appContext.xml");

    JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);
    jdbcTemplate.execute("INSERT INTO event (title, event_date) VALUES ('Java Meetup', '2016-11-18 19:00:00')");
    jdbcTemplate.execute("INSERT INTO event (title, event_date) VALUES ('Python Meetup', '2016-11-20 18:00:00')");
    jdbcTemplate.execute("INSERT INTO event (title, event_date) VALUES ('Go Meetup', '2016-11-20 18:30:00')");
    jdbcTemplate.execute("INSERT INTO event (title, event_date) VALUES ('Hackathon', '2016-11-20 19:30:00')");

    EventDao eventDao = context.getBean(EventDao.class);
    eventDao.getEvent(2);
    eventDao.getEvent(2);
  }
}
