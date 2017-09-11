package com.akhalikov.backend.users;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Entity
@Table(name = "users")
@AttributeOverride(name = "id", column = @Column(name = "user_id", nullable = false))
public class User {     // it does not implement Serializable
                        // because we do not plan to use it remotely as detached object.

  @Id                   // The identifier attribute
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "created_timestamp")
  private LocalDateTime createdTs;

  @Column(name = "updated_timestamp")
  private LocalDateTime updatedDateTime;

  @Column(name = "date_time")
  private ZonedDateTime dateTime; // date time with time zone

  // constructor for Hibernate
  // the entity class must have a public, protected or package-private no-argument constructor
  User() {
  }

  public User(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
    createdTs = LocalDateTime.now();
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

  public LocalDateTime getCreatedTs() {
    return createdTs;
  }

  public LocalDateTime getUpdatedDateTime() {
    return updatedDateTime;
  }

  void setUpdatedDateTime(LocalDateTime updatedDateTime) {
    this.updatedDateTime = updatedDateTime;
  }

  public ZonedDateTime getDateTime() {
    return dateTime;
  }

  public void setDateTime(ZonedDateTime dateTime) {
    this.dateTime = dateTime;
  }

  @Override
  public String toString() {
    return String.format("%s{id=%d, firstName=%s, lastName=%s}",
        getClass().getSimpleName(), id, firstName, lastName);
  }
}
