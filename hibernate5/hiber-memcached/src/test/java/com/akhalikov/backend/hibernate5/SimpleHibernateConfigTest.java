package com.akhalikov.backend.hibernate5;

import com.akhalikov.backend.hibernate5.event.Event;
import com.akhalikov.backend.hibernate5.event.EventDao;
import org.hibernate.Session;
import org.hibernate.stat.Statistics;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.List;

import static org.testng.Assert.assertEquals;

@ContextConfiguration(classes = {AppContextConfig.class, SimpleHibernateConfig.class})
public class SimpleHibernateConfigTest extends BaseTest {
  @Inject
  private EventDao eventDao;

  private Statistics statistics;

  @BeforeMethod
  public void setUp() throws Exception {
    statistics = sessionFactory.getStatistics();
    statistics.setStatisticsEnabled(true);
  }

  @Test
  public void shouldGetEvents() throws Exception {
    List<Event> events = eventDao.getEvents();
    assertEquals(events.size(), 2);
    assertEquals(events.get(0).getTitle(), "Java Meetup");
    assertEquals(events.get(1).getTitle(), "Python Meetup");
  }

  @Test
  public void testThatL2CacheIsOff() throws Exception {
    Session session = sessionFactory.openSession();
    session.get(Event.class, 1);
    session.close();

    session = sessionFactory.openSession();
    session.get(Event.class, 1);
    session.close();

    long l2CacheHits = statistics.getSecondLevelCacheHitCount();
    assertEquals(l2CacheHits, 0);
  }
}
