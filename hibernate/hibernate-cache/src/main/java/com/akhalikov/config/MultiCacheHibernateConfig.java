package com.akhalikov.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

import javax.sql.DataSource;

@Configuration
@ImportResource("appContext.xml")
public class MultiCacheHibernateConfig {
  @Bean
  FactoryBean<SessionFactory> sessionFactory(final DataSource dataSource) {
    final LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
    sessionFactoryBean.setDataSource(dataSource);
    sessionFactoryBean.setConfigLocation(new ClassPathResource("multicache/hibernate.cfg.xml"));
    return sessionFactoryBean;
  }
}
