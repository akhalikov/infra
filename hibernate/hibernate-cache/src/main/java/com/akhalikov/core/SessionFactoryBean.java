package com.akhalikov.core;

import com.akhalikov.integration.EventListenersIntegrator;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.BootstrapServiceRegistry;
import org.hibernate.boot.registry.BootstrapServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;

public class SessionFactoryBean extends LocalSessionFactoryBean {

  @Override
  protected SessionFactory buildSessionFactory(LocalSessionFactoryBuilder sfb) {
    BootstrapServiceRegistryBuilder bootstrapRegistryBuilder = new BootstrapServiceRegistryBuilder();
    bootstrapRegistryBuilder.applyClassLoader(getResourceLoader().getClassLoader());
    bootstrapRegistryBuilder.applyIntegrator(new EventListenersIntegrator());
    BootstrapServiceRegistry bootstrapRegistry = bootstrapRegistryBuilder.build();

    StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder(bootstrapRegistry);
    serviceRegistryBuilder.applySettings(getConfiguration().getProperties());
    serviceRegistryBuilder.configure();

    StandardServiceRegistry serviceRegistry = serviceRegistryBuilder.build();

    return sfb.buildSessionFactory(serviceRegistry);
  }
}
