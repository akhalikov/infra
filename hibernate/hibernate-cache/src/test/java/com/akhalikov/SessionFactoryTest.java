package com.akhalikov;

import com.akhalikov.entity.Event;
import org.hibernate.Cache;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.springframework.core.io.ClassPathResource;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

public class SessionFactoryTest extends TestBase {
  private static SessionFactory sessionFactory;
  private static EventDao eventDao;
  private static Statistics statistics;

  @BeforeClass
  public static void setUpClass() throws Exception {
    sessionFactory = createSessionFactory(dataSource);
    eventDao = new EventDao(sessionFactory);

    statistics = sessionFactory.getStatistics();
    statistics.setStatisticsEnabled(true);
    statistics.clear();
  }

  @BeforeMethod
  public void setUp() throws Exception {
    addTestData();
  }

  @Test
  public void shouldGetEvents() throws Exception {
    List<Event> events = eventDao.getEvents();

    assertEquals(events.size(), 4);

    assertEquals(events.get(0).getTitle(), "Java Meetup");
    assertEquals(events.get(1).getTitle(), "Python Meetup");
    assertEquals(events.get(2).getTitle(), "Go Meetup");
    assertEquals(events.get(3).getTitle(), "Hackathon");
  }

  @Test
  public void testThatL2CacheIsOff() throws Exception {
    final int eventId = 1;

    eventDao.getEvent(eventId);
    eventDao.getEvent(eventId);

    assertEquals(statistics.getSecondLevelCacheHitCount(), 0);

    Cache cache = sessionFactory.getCache();
    assertFalse(cache.containsEntity(Event.class, eventId));
  }

  private static SessionFactory createSessionFactory(final DataSource dataSource) throws IOException {
    final LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
    sessionFactoryBean.setDataSource(dataSource);

    sessionFactoryBean.setConfigLocation(new ClassPathResource("hibernate.cfg.xml"));
    sessionFactoryBean.afterPropertiesSet();

    return sessionFactoryBean.getObject();
  }
}