package com.akhalikov.core;

import org.hibernate.boot.registry.BootstrapServiceRegistry;
import org.hibernate.boot.registry.BootstrapServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.integrator.spi.Integrator;
import org.springframework.core.io.ResourceLoader;

import java.util.Properties;

class HibernateServiceRegistryBuilder {

  static BootstrapServiceRegistry createBootstrapServiceRegistry(ResourceLoader resourceLoader,
                                                                 Integrator... integrators) {
    BootstrapServiceRegistryBuilder bootstrapRegistryBuilder = new BootstrapServiceRegistryBuilder();

    for (Integrator integrator: integrators) {
      bootstrapRegistryBuilder.applyIntegrator(integrator);
    }

    return bootstrapRegistryBuilder.build();
  }

  static StandardServiceRegistry createStandardServiceRegistry(BootstrapServiceRegistry bootstrapServiceRegistry,
                                                               Properties hibernateProperties) {
    StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder(bootstrapServiceRegistry);
    serviceRegistryBuilder.applySettings(hibernateProperties);

    return serviceRegistryBuilder.build();
  }
}
