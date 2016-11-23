package com.akhalikov.config;

import com.akhalikov.event.EventDao;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppContextConfig {
  @Bean
  EventDao eventDao(final SessionFactory sessionFactory) {
    return new EventDao(sessionFactory);
  }
}
