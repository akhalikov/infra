package akhalikov.spring5webapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AppProdConfig {
  @Bean
  TestService testService() {
    return new TestService();
  }
}
