package example1;

class MessageController {
  private final MessageService messageService;

  MessageController(MessageService messageService) {
    this.messageService = messageService;
  }

  void run() {
    messageService.printMessage("Hello World!");
  }
}
