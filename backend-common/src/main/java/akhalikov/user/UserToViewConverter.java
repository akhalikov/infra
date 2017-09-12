package akhalikov.user;

import java.util.function.Function;

public class UserToViewConverter implements Function<User, UserView> {
  @Override
  public UserView apply(User user) {
    return new UserView(user.id, user.firstName, user.lastName, user.birthDate);
  }
}
