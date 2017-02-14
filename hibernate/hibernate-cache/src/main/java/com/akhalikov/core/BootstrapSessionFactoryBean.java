package com.akhalikov.core;

import static com.akhalikov.core.HibernateServiceRegistryBuilder.createBootstrapServiceRegistry;
import static com.akhalikov.core.HibernateServiceRegistryBuilder.createStandardServiceRegistry;
import com.akhalikov.integration.EventListenersIntegrator;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.BootstrapServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;

public class BootstrapSessionFactoryBean extends LocalSessionFactoryBean {

  @Override
  protected SessionFactory buildSessionFactory(LocalSessionFactoryBuilder sfb) {
    BootstrapServiceRegistry bootstrapRegistry = createBootstrapServiceRegistry(
        getResourceLoader(), new EventListenersIntegrator());

    StandardServiceRegistry serviceRegistry = createStandardServiceRegistry(bootstrapRegistry,
        getConfiguration().getProperties());

    return sfb.buildSessionFactory(serviceRegistry);
  }
}
