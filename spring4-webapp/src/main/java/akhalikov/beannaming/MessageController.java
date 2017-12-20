package akhalikov.spring4webapp.beannaming;

class MessageController {
  private final MessageService messageService;

  MessageController(MessageService messageService) {
    this.messageService = messageService;
  }

  void run() {
    messageService.printMessage("Hello World!");
  }
}
