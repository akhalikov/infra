package com.akhalikov.backend.jetty.config;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
public class JettyConfig implements ApplicationContextAware {
  private ApplicationContext applicationContext;

  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

  @Bean
  ServletHolder dispatcherServlet() {
    AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
    ctx.setParent(applicationContext);

    DispatcherServlet dispatcherServlet = new DispatcherServlet(ctx);
    ServletHolder holder = new ServletHolder("dispatcherServlet", dispatcherServlet);
    holder.setInitOrder(1);
    return holder;
  }

  @Bean
  ThreadPool jettyThreadPool() {
    return new QueuedThreadPool(8, 2);
  }

  @Bean(initMethod = "start", destroyMethod = "stop")
  Server jettyServer(ThreadPool jettyThreadPool, ServletHolder dispatcherServlet) throws Exception {
    Server server = createJettyServer(jettyThreadPool);

    WebAppContext webApp = createWebAppContext();
    webApp.getServletHandler().addServletWithMapping(dispatcherServlet, "/");

    server.setHandler(webApp);

    return server;
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

  private static WebAppContext createWebAppContext() {
    final WebAppContext webApp = new WebAppContext();
    webApp.setContextPath("/");
    webApp.setResourceBase("src/main/webapp");
    webApp.setDescriptor("target/web.xml");
    webApp.setClassLoader(Thread.currentThread().getContextClassLoader());
    return webApp;
  }
}
