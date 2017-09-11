package akhalikov.user;

import java.util.Date;

@SuppressWarnings("WeakerAccess")
public class UserView {
  public int id;
  public String firstName;
  public String lastName;
  public Date birthDate;

  public UserView() {
  }

  public UserView(int id, String firstName, String lastName, Date birthDate) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthDate = birthDate;
  }
}
