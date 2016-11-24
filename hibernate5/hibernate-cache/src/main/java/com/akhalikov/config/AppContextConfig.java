package com.akhalikov.config;

import com.akhalikov.DocumentDao;
import com.akhalikov.EventDao;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppContextConfig {
  @Bean
  EventDao eventDao(final SessionFactory sessionFactory) {
    return new EventDao(sessionFactory);
  }

  @Bean
  DocumentDao documentDao(final SessionFactory sessionFactory) {
    return new DocumentDao(sessionFactory);
  }
}
