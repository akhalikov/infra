package akhalikov.jetty.config;

import akhalikov.jetty.HelloController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(JettyConfig.class)
public class AppConfig {
  @Bean
  HelloController helloController() {
    return new HelloController();
  }
}
