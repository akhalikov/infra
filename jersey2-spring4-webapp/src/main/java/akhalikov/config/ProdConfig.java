package akhalikov.config;

import akhalikov.rs.hello.HelloResource;
import akhalikov.rs.hello.HelloService;
import akhalikov.utils.properties.PropertyUtils;
import akhalikov.utils.properties.Settings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class ProdConfig {
  @Bean
  HelloService helloService() {
    return new HelloService();
  }

  @Bean
  HelloResource helloResource(HelloService helloService) {
    return new HelloResource(helloService);
  }

  @Bean
  Settings settings() throws Exception {
    Properties properties = PropertyUtils.fromFileInSettingsDir("settings.properties");
    return new Settings(properties);
  }
}
