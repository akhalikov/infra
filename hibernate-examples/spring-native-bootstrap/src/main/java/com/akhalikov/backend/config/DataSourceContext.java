package com.akhalikov.backend.config;

import com.akhalikov.backend.utils.DataSourceFactory;
import com.akhalikov.backend.utils.PropertiesFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
class DataSourceContext {

  @Bean
  DataSource dataSource() throws Exception {
    return DataSourceFactory
      .createC3P0DataSource(PropertiesFactory.load());
  }
}
