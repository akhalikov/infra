package com.akhalikov.backend.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class GuiceMain {

  public static void main(String[] args) {
    final Injector injector = createInjector(); // create graph of beans

    // Bean bean = injector.getInstance(Bean.class); // get bean from the graph

    // bean.doSomething();
  }

  private static Injector createInjector() {
    return Guice.createInjector(new CommonModule(), new ProdModule());
  }

  private GuiceMain() {
  }
}
