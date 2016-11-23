package com.akhalikov;

import com.akhalikov.config.AppContextConfig;
import com.akhalikov.config.MemcachedHibernateConfig;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

@ContextConfiguration(classes = {AppContextConfig.class, MemcachedHibernateConfig.class})
public class MemcachedSessionFactoryTest extends SessionFactoryTestBase {
  @Test
  public void shouldUseL2Cache() throws Exception {
    getEventFromSessionTwice();

    long l2CacheHits = statistics.getSecondLevelCacheHitCount();
    assertEquals(l2CacheHits, 1);
  }
}
