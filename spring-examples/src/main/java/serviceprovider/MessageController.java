package serviceprovider;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;

class MessageController {
  @Inject
  @Named("basicMessageService")
  private Provider<MessageService> messageServiceProvider;

  void run() {
    messageServiceProvider.get().printMessage("Hello World!");
  }
}
