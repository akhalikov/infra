package com.akhalikov;

import com.akhalikov.events.Event;
import com.akhalikov.events.EventDao;
import org.hibernate.CacheMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.internal.CacheImpl;
import org.hibernate.stat.Statistics;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class EhCacheTest extends TestBase {
  private static SessionFactory sessionFactory;
  private static EventDao eventDao;
  @BeforeClass
  public void setUpClass() throws Exception {
    hibernateProperties.setProperty("hibernate.cache.use_second_level_cache", "true");
    hibernateProperties.setProperty("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.EhCacheRegionFactory");
    hibernateProperties.setProperty("net.sf.ehcache.configurationResourceName", "ehcache.xml");

    sessionFactory = createSessionFactory();

    eventDao = new EventDao(sessionFactory);
  }

  @Test
  public void testEhCacheHitMiss() throws Exception {
    Statistics statistics = createStatistics(sessionFactory);

    final int eventId = 1;

    assertStatistics(statistics, 0, 0, 0);

    eventDao.getEvent(eventId);
    eventDao.getEvent(eventId);

    assertStatistics(statistics, 1, 1, 1);

    CacheImpl cache = (CacheImpl) sessionFactory.getCache();

    assertTrue(cache.containsEntity(Event.class, eventId));

    eventDao.getEvent(0);

    assertStatistics(statistics, 1, 1, 2);

    cache.evictAll();
  }

  @Test
  public void testEhCacheMultiLoad() throws Exception {
    Statistics statistics = createStatistics(sessionFactory);

    CacheImpl cache = (CacheImpl) sessionFactory.getCache();
    assertFalse(cache.containsEntity(Event.class, 1));
    assertStatistics(statistics, 0, 0, 0);

    try (Session session = sessionFactory.openSession()) {
      Event event = session.get(Event.class, 1);
      assertNotNull(event.getId());
    }

    assertStatistics(statistics, 1, 0, 1);
    assertTrue(cache.containsEntity(Event.class, 1));

    try (Session session = sessionFactory.openSession()) {
      Event event = session.get(Event.class, 1);
      assertNotNull(event.getId());

      List<Event> events = session.byMultipleIds(Event.class)
          .enableSessionCheck(true)
          .with(CacheMode.NORMAL)
          .multiLoad(1);
      assertEquals(1, events.get(0).getId().intValue());
    }

    assertStatistics(statistics, 1, 1, 1);

    cache.evictAll();
  }

  private static SessionFactory createSessionFactory() throws Exception {
    LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
    sessionFactoryBean.setDataSource(dataSource);
    sessionFactoryBean.setHibernateProperties(hibernateProperties);
    sessionFactoryBean.setPackagesToScan("com.akhalikov.events");
    sessionFactoryBean.afterPropertiesSet();
    return sessionFactoryBean.getObject();
  }
}
