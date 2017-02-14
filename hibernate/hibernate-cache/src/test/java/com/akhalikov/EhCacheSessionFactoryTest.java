package com.akhalikov;

import com.akhalikov.core.SessionFactoryBean;
import com.akhalikov.entity.Event;
import org.hibernate.Cache;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.springframework.core.io.ClassPathResource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.sql.DataSource;
import java.io.IOException;

public class EhCacheSessionFactoryTest extends TestBase {
  private static SessionFactory sessionFactory;
  private static EventDao eventDao;
  private static Statistics statistics;

  @BeforeClass
  public void setUp() throws Exception {
    sessionFactory = createSessionFactory(dataSource);
    statistics = createStatistics(sessionFactory);
    eventDao = new EventDao(sessionFactory);
  }

  @Test
  public void testEhCacheHitMiss() throws Exception {
    addTestData();

    final int eventId = 1;

    eventDao.getEvent(eventId);
    eventDao.getEvent(eventId);
    eventDao.getEvent(eventId);

    assertEquals(statistics.getSecondLevelCacheMissCount(), 1);
    assertEquals(statistics.getSecondLevelCacheHitCount(), 2);

    Cache cache = sessionFactory.getCache();
    assertTrue(cache.containsEntity(Event.class, eventId));

    eventDao.getEvent(0);

    assertEquals(statistics.getSecondLevelCacheMissCount(), 2);
  }

  private static SessionFactory createSessionFactory(final DataSource dataSource) throws IOException {
    final LocalSessionFactoryBean sessionFactoryBean = new SessionFactoryBean();
    sessionFactoryBean.setDataSource(dataSource);

    sessionFactoryBean.setConfigLocation(new ClassPathResource("ehcache/hibernate.cfg.xml"));
    sessionFactoryBean.afterPropertiesSet();

    return sessionFactoryBean.getObject();
  }
}
