package com.akhalikov.hibermem;

import com.akhalikov.hibermem.event.Event;
import org.hibernate.Session;
import org.hibernate.stat.Statistics;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

@ContextConfiguration(classes = {AppContextConfig.class, MemcachedHibernateConfig.class})
public class MemcachedHibernateConfigTest extends BaseTest {
  private Statistics statistics;

  @BeforeMethod
  public void setUp() throws Exception {
    statistics = sessionFactory.getStatistics();
    statistics.setStatisticsEnabled(true);
  }

  @Test
  public void testThatL2CacheWorks() throws Exception {
    Session session = sessionFactory.openSession();
    session.get(Event.class, 1);
    session.close();

    session = sessionFactory.openSession();
    session.get(Event.class, 1);
    session.close();

    long l2CacheHits = statistics.getSecondLevelCacheHitCount();
    assertEquals(l2CacheHits, 1);
  }
}