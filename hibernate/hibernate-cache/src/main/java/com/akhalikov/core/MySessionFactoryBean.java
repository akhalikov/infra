package com.akhalikov.core;

import com.akhalikov.integration.EventListenersIntegrator;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.BootstrapServiceRegistry;
import org.hibernate.boot.registry.BootstrapServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;

public class MySessionFactoryBean extends LocalSessionFactoryBean {
  @Override
  protected SessionFactory buildSessionFactory(LocalSessionFactoryBuilder sfb) {
    BootstrapServiceRegistryBuilder bootstrapRegistryBuilder = new BootstrapServiceRegistryBuilder();
    bootstrapRegistryBuilder.applyIntegrator(new EventListenersIntegrator());
    BootstrapServiceRegistry bootstrapServiceRegistry = bootstrapRegistryBuilder.build();

    StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder(bootstrapServiceRegistry)
        .configure("hibernate.cfg.xml")
        .build();

    return sfb.buildSessionFactory(serviceRegistry);
  }
}
