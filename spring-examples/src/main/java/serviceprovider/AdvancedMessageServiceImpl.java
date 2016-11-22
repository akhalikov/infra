package serviceprovider;

class AdvancedMessageServiceImpl implements MessageService {
  @Override
  public void printMessage(String message) {
    System.out.println("AdvancedMessageServiceImpl: " + message);
  }
}
