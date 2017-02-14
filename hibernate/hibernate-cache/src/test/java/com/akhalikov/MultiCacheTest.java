package com.akhalikov;

import com.akhalikov.core.MultiCacheBootstrapSessionFactoryBean;
import com.akhalikov.entity.Event;
import org.hibernate.SessionFactory;
import org.hibernate.internal.CacheImpl;
import org.hibernate.stat.Statistics;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import static org.testng.Assert.assertFalse;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class MultiCacheTest extends TestBase {
  private static SessionFactory sessionFactory;
  private static EventDao eventDao;
  private static Statistics statistics;

  @BeforeClass
  public void setUp() throws Exception {
    hibernateProperties.setProperty("hibernate.cache.use_second_level_cache", "true");
    hibernateProperties.setProperty("hibernate.memcached.cacheTimeSeconds", "1");

    sessionFactory = createSessionFactory();

    statistics = createStatistics(sessionFactory);

    eventDao = new EventDao(sessionFactory);
  }

  @Test
  public void testLocalCacheHitMiss() throws Exception {
    addTestData();

    assertStatistics(statistics, 0, 0, 0);

    final int eventId = 1;

    CacheImpl cache = (CacheImpl) sessionFactory.getCache();
    assertFalse(cache.containsEntity(Event.class, eventId), "cache is not empty");

    eventDao.getEvent(eventId);
    eventDao.getEvent(eventId);

    assertStatistics(statistics, 1, 1, 1);

    eventDao.getEvent(0);

    assertStatistics(statistics, 1, 1, 2);
  }

  private static SessionFactory createSessionFactory() throws Exception {
    LocalSessionFactoryBean sessionFactoryBean = new MultiCacheBootstrapSessionFactoryBean();
    sessionFactoryBean.setDataSource(dataSource);
    sessionFactoryBean.setHibernateProperties(hibernateProperties);
    sessionFactoryBean.setPackagesToScan("com.akhalikov.entity");
    sessionFactoryBean.afterPropertiesSet();
    return sessionFactoryBean.getObject();
  }
}
