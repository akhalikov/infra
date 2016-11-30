package com.akhalikov;

import com.akhalikov.config.EhCacheHibernateConfig;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = EhCacheHibernateConfig.class)
public class EhCacheSessionFactoryTest extends CacheTestBase {
}
