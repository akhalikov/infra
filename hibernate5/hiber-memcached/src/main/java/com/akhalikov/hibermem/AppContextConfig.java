package com.akhalikov.hibermem;

import com.akhalikov.hibermem.event.EventDao;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DataSourceContextConfig.class)
class AppContextConfig {
  @Bean
  EventDao eventDao(final SessionFactory sessionFactory) {
    return new EventDao(sessionFactory);
  }
}
