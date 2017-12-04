package example1;

public class NewMessageServiceImpl implements MessageService {

  @Override
  public void printMessage(String message) {
    System.out.println("new message service: " + message);
  }
}
