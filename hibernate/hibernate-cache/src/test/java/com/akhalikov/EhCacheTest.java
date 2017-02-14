package com.akhalikov;

import com.akhalikov.entity.Event;
import org.hibernate.SessionFactory;
import org.hibernate.internal.CacheImpl;
import org.hibernate.stat.Statistics;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class EhCacheTest extends TestBase {
  private static SessionFactory sessionFactory;
  private static EventDao eventDao;
  private static Statistics statistics;

  @BeforeClass
  public void setUp() throws Exception {
    hibernateProperties.setProperty("hibernate.cache.use_second_level_cache", "true");
    hibernateProperties.setProperty("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.EhCacheRegionFactory");
    hibernateProperties.setProperty("net.sf.ehcache.configurationResourceName", "ehcache.xml");

    sessionFactory = createSessionFactory();

    statistics = createStatistics(sessionFactory);

    eventDao = new EventDao(sessionFactory);
  }

  @Test
  public void testEhCacheHitMiss() throws Exception {
    addTestData();

    final int eventId = 1;

    assertStatistics(statistics, 0, 0, 0);

    eventDao.getEvent(eventId);
    eventDao.getEvent(eventId);

    assertStatistics(statistics, 1, 1, 1);

    CacheImpl cache = (CacheImpl) sessionFactory.getCache();

    assertTrue(cache.containsEntity(Event.class, eventId));

    eventDao.getEvent(0);

    assertStatistics(statistics, 1, 1, 2);
  }

  private static SessionFactory createSessionFactory() throws Exception {
    LocalSessionFactoryBean sessionFactoryBean = createBootstrapSessionFactoryBean();
    sessionFactoryBean.setAnnotatedClasses(Event.class);
    sessionFactoryBean.afterPropertiesSet();
    return sessionFactoryBean.getObject();
  }
}
