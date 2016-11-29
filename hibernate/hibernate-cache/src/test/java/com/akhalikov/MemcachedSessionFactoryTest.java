package com.akhalikov;

import com.akhalikov.config.AppContextConfig;
import com.akhalikov.config.MemcachedHibernateConfig;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {AppContextConfig.class, MemcachedHibernateConfig.class})
public class MemcachedSessionFactoryTest extends CacheTestBase {
}
