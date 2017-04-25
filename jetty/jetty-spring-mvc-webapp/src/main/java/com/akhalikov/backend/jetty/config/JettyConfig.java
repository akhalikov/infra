package com.akhalikov.backend.jetty.config;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;

@Configuration
public class JettyConfig implements ApplicationContextAware {
  private ApplicationContext applicationContext; // spring will inject it's application context here

  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

  @Bean
  ThreadPool jettyThreadPool() {
    return new QueuedThreadPool(8, 2);
  }

  @Bean(initMethod = "start", destroyMethod = "stop")
  Server jettyServer(ThreadPool jettyThreadPool, WebAppContext jettyWebAppContext) throws Exception {
    Server server = createJettyServer(jettyThreadPool);
    server.setHandler(jettyWebAppContext);
    return server;
  }

  @Bean
  WebAppContext jettyWebAppContext() {
    final WebAppContext jettyContext = new WebAppContext();
    jettyContext.setContextPath("/");
    jettyContext.setResourceBase("src/main/webapp");
    jettyContext.setDescriptor("target/web.xml");
    jettyContext.setClassLoader(Thread.currentThread().getContextClassLoader());

    GenericWebApplicationContext springWebApplicationContext = new GenericWebApplicationContext();
    springWebApplicationContext.setParent(applicationContext);
    springWebApplicationContext.refresh();

    jettyContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, springWebApplicationContext);

    return jettyContext;
  }

  private static Server createJettyServer(ThreadPool threadPool) {
    Server server = new Server(threadPool);
    server.setStopAtShutdown(true);
    server.setStopTimeout(5000);

    int acceptors = -1; // -1 means auto
    int selectors = -1; // -1 means auto
    int port = 8080;

    ServerConnector serverConnector = new ServerConnector(server, acceptors, selectors);
    serverConnector.setPort(port);
    server.addConnector(serverConnector);

    return server;
  }
}
