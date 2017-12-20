package akhalikov.spring4webapp.beannaming;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import javax.inject.Named;

@Configuration
class Config1 {

  @Bean
  MessageService messageService() {
    return new MessageServiceImpl();
  }

  @Bean
  @Inject
  @Named("messageService")
  MessageController messageController(MessageService messageService) {
    return new MessageController(messageService);
  }
}
