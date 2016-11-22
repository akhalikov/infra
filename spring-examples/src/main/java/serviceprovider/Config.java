package serviceprovider;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class Config {
  @Bean
  MessageService basicMessageService() {
    return new BasicMessageServiceImpl();
  }

  @Bean
  MessageService advancedMessageService() {
    return new AdvancedMessageServiceImpl();
  }

  @Bean
  MessageController messageController() {
    return new MessageController();
  }
}
