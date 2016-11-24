package com.akhalikov;

import com.akhalikov.config.AppContextConfig;
import com.akhalikov.config.EhCacheHibernateConfig;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {AppContextConfig.class, EhCacheHibernateConfig.class})
public class EhCacheSessionFactoryTest extends CacheTestBase {
}
