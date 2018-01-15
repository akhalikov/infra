package akhalikov.spring4webinitializer;

import akhalikov.spring4webapp.ProdAppConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

public class MyInitializer implements WebApplicationInitializer {

  @Override
  public void onStartup(ServletContext container) {
    System.out.println("webapp startup");

    AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
    rootContext.register(ProdAppConfig.class);
    container.addListener(new ContextLoaderListener(rootContext));

    DispatcherServlet servlet = new DispatcherServlet(rootContext);
    ServletRegistration.Dynamic registration = container.addServlet("test", servlet);
    registration.setLoadOnStartup(1);
    registration.addMapping("/");
  }
}
