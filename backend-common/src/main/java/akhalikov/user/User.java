package akhalikov.user;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

@Entity
@Table(name = "users")
@AttributeOverride(name = "id", column = @Column(name = "user_id", nullable = false))
public class User {     // it does not implement Serializable
                        // because we do not plan to use it remotely as detached object.

  @Id                   // The identifier attribute
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer id;

  @Column(name = "first_name")
  String firstName;

  @Column(name = "last_name")
  String lastName;

  @Column(name = "birth_date")
  Date birthDate;

  @Column(name = "created_timestamp")
  LocalDateTime createdTime;

  @Column(name = "updated_timestamp")
  LocalDateTime updatedTime;

  @Column(name = "test_date_time")
  ZonedDateTime testDateTime; // date time with time zone

  // constructor for Hibernate
  // the entity class must have a public, protected or package-private no-argument constructor
  User() {
  }

  public User(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
    createdTime = LocalDateTime.now();
  }

  public Integer getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Date getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(Date birthDate) {
    this.birthDate = birthDate;
  }

  public LocalDateTime getCreatedTime() {
    return createdTime;
  }

  public LocalDateTime getUpdatedTime() {
    return updatedTime;
  }

  void setUpdatedTime(LocalDateTime updatedTime) {
    this.updatedTime = updatedTime;
  }

  public ZonedDateTime getTestDateTime() {
    return testDateTime;
  }

  public void setTestDateTime(ZonedDateTime testDateTime) {
    this.testDateTime = testDateTime;
  }

  @Override
  public String toString() {
    return String.format("%s{id=%d, firstName=%s, lastName=%s}",
        getClass().getSimpleName(), id, firstName, lastName);
  }
}
