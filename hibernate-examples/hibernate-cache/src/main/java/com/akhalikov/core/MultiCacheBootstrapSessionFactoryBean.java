package com.akhalikov.core;

import static com.akhalikov.core.HibernateServiceRegistryBuilder.createBootstrapServiceRegistry;
import com.akhalikov.integration.HibernateListenersIntegrator;
import com.akhalikov.multicache.MultiCacheRegionFactory;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.BootstrapServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cache.spi.RegionFactory;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;

public class MultiCacheBootstrapSessionFactoryBean extends LocalSessionFactoryBean {
  private final HibernateListenersIntegrator eventListenersIntegrator = new HibernateListenersIntegrator();

  @Override
  protected SessionFactory buildSessionFactory(LocalSessionFactoryBuilder sfb) {
    BootstrapServiceRegistry bootstrapRegistry = createBootstrapServiceRegistry(getResourceLoader(), eventListenersIntegrator);

    StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder(bootstrapRegistry);
    serviceRegistryBuilder.applySettings(getConfiguration().getProperties());
    serviceRegistryBuilder.addService(RegionFactory.class, new MultiCacheRegionFactory());

    return sfb.buildSessionFactory(serviceRegistryBuilder.build());
  }
}
