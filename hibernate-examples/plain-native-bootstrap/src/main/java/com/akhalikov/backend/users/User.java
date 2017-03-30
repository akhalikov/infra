package com.akhalikov.backend.users;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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

  // constructor for Hibernate
  // the entity class must have a public, protected or package-private no-argument constructor
  User() {
  }

  public User(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
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
