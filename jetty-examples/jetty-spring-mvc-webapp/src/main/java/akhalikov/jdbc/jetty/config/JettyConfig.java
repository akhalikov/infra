package akhalikov.jdbc.jetty.config;

import static akhalikov.jdbc.jetty.JettyFactory.createJettyServer;
import static akhalikov.jdbc.jetty.JettyFactory.createWebAppContext;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.component.AbstractLifeCycle.AbstractLifeCycleListener;
import org.eclipse.jetty.util.component.LifeCycle;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;

@Configuration
public class JettyConfig implements ApplicationContextAware {
  private static final Logger LOGGER = LoggerFactory.getLogger(JettyConfig.class);

  private ApplicationContext applicationContext; // for spring context

  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

  @Bean
  ThreadPool jettyThreadPool() {
    return new QueuedThreadPool(8, 2);
  }

  @Bean(initMethod = "start", destroyMethod = "stop")
  Server jettyServer(ThreadPool jettyThreadPool) throws Exception {
    final Server server = createJettyServer(jettyThreadPool);
    final WebAppContext webApp = createWebAppContext();

    GenericWebApplicationContext springWebApplicationContext = new GenericWebApplicationContext();
    springWebApplicationContext.setParent(applicationContext);
    springWebApplicationContext.refresh();

    webApp.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, springWebApplicationContext);

    server.setHandler(webApp);
    server.addLifeCycleListener(new AbstractLifeCycleListener() {
      @Override
      public void lifeCycleStarted(LifeCycle lifeCycle) {
        if (!webApp.isAvailable()) {
          LOGGER.error("failed to init web application: {}",
              webApp.getUnavailableException().toString(), webApp.getUnavailableException());
          System.exit(1);
        }
      }
    });
    return server;
  }
}
