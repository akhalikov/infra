package com.akhalikov.backend.guice;

import com.google.inject.AbstractModule;

public class CommonModule extends AbstractModule {
  @Override
  protected void configure() {
    // basic example: bind(Abstract.class).to(Implementation.class);

    // also:
    // bind(Implementation.class);
    // bind(UserDAO.class).toProvider(Provider<Implementation.class>);
  }
}
