package com.akhalikov.config;

import com.akhalikov.integration.HibernateSpringIntegrator;
import com.akhalikov.integration.MyLoadEventListener;
import org.hibernate.SessionFactory;
import org.hibernate.event.spi.LoadEventListener;
import org.hibernate.integrator.spi.Integrator;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

import javax.sql.DataSource;

@Configuration
@Import(DataSourceContextConfig.class)
public class MemcachedHibernateConfig {
  @Bean
  FactoryBean<SessionFactory> sessionFactory(final DataSource dataSource) {
    final LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
    sessionFactoryBean.setDataSource(dataSource);
    sessionFactoryBean.setConfigLocation(new ClassPathResource("memcached/hibernate.cfg.xml"));
    return sessionFactoryBean;
  }

  @Bean
  LoadEventListener loadEventListener() {
    return new MyLoadEventListener();
  }

  @Bean
  HibernateSpringIntegrator hibernateSpringIntegrator() {
    return new HibernateSpringIntegrator();
  }
}
