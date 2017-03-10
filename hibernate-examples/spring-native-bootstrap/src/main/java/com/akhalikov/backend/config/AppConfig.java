package com.akhalikov.backend.config;

import com.akhalikov.backend.proxy.ProxyDataSource;
import com.akhalikov.backend.users.UserService;
import com.akhalikov.backend.users.UserSpringDao;
import com.akhalikov.backend.utils.DataSourceFactory;
import com.akhalikov.backend.utils.PropertiesFactory;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class AppConfig {

  @Bean
  DataSource dataSource() throws Exception {
    ComboPooledDataSource dataSource = DataSourceFactory.createC3P0DataSource(PropertiesFactory.load());
    return new ProxyDataSource(dataSource);
  }

  @Bean
  LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
    LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
    sessionFactory.setDataSource(dataSource);
    sessionFactory.setConfigLocation(new ClassPathResource("hibernate.cfg.xml"));

    Properties properties = PropertiesFactory.fromFile("hibernate.properties");
    sessionFactory.setHibernateProperties(properties);
    return sessionFactory;
  }

  @Bean
  PlatformTransactionManager transactionManager(SessionFactory sessionFactory,
                                                DataSource dataSource) {
    HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);
    transactionManager.setDataSource(dataSource);
    return transactionManager;
  }

  @Bean
  UserSpringDao userDao(SessionFactory sessionFactory) {
    return new UserSpringDao(sessionFactory);
  }

  @Bean
  UserService userService(UserSpringDao userSpringDao) {
    return new UserService(userSpringDao);
  }
}