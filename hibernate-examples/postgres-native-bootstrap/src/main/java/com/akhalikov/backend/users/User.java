package com.akhalikov.backend.users;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
@AttributeOverride(name = "id", column = @Column(name = "user_id", nullable = false))
public class User {     // it does not implement Serializable
                        // because we do not plan to use it remotely as detached object.

  @Id                   // The identifier attribute
  private Integer id;

  private String firstName;

  private String lastName;

  // constructor for Hibernate
  // the entity class must have a public, protected or package-private no-argument constructor
  User() {
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

  @Override
  public String toString() {
    return String.format("%s{id=%d, firstName=%s, lastName=%s}",
        getClass().getSimpleName(), id, firstName, lastName);
  }
}
