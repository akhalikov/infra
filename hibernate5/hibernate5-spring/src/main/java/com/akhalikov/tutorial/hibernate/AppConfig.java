package com.akhalikov.tutorial.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

import javax.sql.DataSource;

@Configuration
public class AppConfig {

  @Bean
  DataSource dataSource() {
    EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
    EmbeddedDatabase embeddedDatabase = builder
        .setType(EmbeddedDatabaseType.H2)
        .addScript("schema.sql")
        .build();
  }

  @Bean
  FactoryBean<SessionFactory> sessionFactory(final DataSource dataSource) {
    final LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
    localSessionFactoryBean.setDataSource(dataSource);
    localSessionFactoryBean.setConfigLocation(new ClassPathResource("hibernate.cfg.xml"));
    return localSessionFactoryBean;
  }
}
