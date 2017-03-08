package com.akhalikov.backend.config;

import com.akhalikov.backend.users.UserDao;
import com.akhalikov.backend.users.UserSpringDao;
import com.akhalikov.backend.utils.PropertiesFactory;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@Import(DataSourceContext.class)
public class AppConfig {

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
  UserDao userDao(SessionFactory sessionFactory) {
    return new UserSpringDao(sessionFactory);
  }
}