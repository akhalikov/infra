package com.akhalikov;

import com.akhalikov.entity.Event;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class SessionFactoryTest extends TestBase {
  private static SessionFactory sessionFactory;
  private static EventDao eventDao;
  private static Statistics statistics;


  @BeforeClass
  public static void setUpClass() throws Exception {
    hibernateProperties.setProperty("hibernate.cache.use_second_level_cache", "false");
    hibernateProperties.setProperty("hibernate.cache.provider_class", "org.hibernate.cache.internal.NoCacheProvider");

    sessionFactory = createSessionFactory();

    statistics = createStatistics(sessionFactory);

    eventDao = new EventDao(sessionFactory);
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

    assertFalse(sessionFactory.getCache().containsEntity(Event.class, eventId));

    assertStatistics(statistics, 0, 0, 0);
  }

  private static SessionFactory createSessionFactory() throws Exception {
    LocalSessionFactoryBean sessionFactoryBean = createBootstrapSessionFactoryBean();
    sessionFactoryBean.setAnnotatedClasses(Event.class);
    sessionFactoryBean.afterPropertiesSet();
    return sessionFactoryBean.getObject();
  }
}