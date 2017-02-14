package com.akhalikov.core;

import static com.akhalikov.core.HibernateServiceRegistryBuilder.createBootstrapServiceRegistry;
import com.akhalikov.integration.EventListenersIntegrator;
import com.akhalikov.multicache.MultiCacheRegionFactory;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.BootstrapServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cache.spi.RegionFactory;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;

public class MultiCacheBootstrapSessionFactoryBean extends LocalSessionFactoryBean {

  @Override
  protected SessionFactory buildSessionFactory(LocalSessionFactoryBuilder sfb) {
    BootstrapServiceRegistry bootstrapRegistry = createBootstrapServiceRegistry(
        getResourceLoader(), new EventListenersIntegrator());

    StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder(bootstrapRegistry);
    serviceRegistryBuilder.applySettings(getConfiguration().getProperties());

    serviceRegistryBuilder.addService(RegionFactory.class, new MultiCacheRegionFactory());

    return sfb.buildSessionFactory(serviceRegistryBuilder.build());
  }
}
