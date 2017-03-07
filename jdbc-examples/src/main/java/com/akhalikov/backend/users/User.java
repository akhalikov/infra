package com.akhalikov.backend.users;

public class User {
  private Integer id;

  private String firstName;

  private String lastName;

  // factory method to create new user
  public static User create(String firstName, String lastName) {
    return new User(null, firstName, lastName);
  }

  // factory method to load existing user from db
  // it's package private because we want only DAO classes from the same package be able to call it
  // it's first parameter cannot be null because existing user should always have ID
  static User existing(int id, String firstName, String lastName) {
    return new User(id, firstName, lastName);
  }

  // private constructor
  private User(Integer id, String firstName, String lastName) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public Integer getId() {
    return id;
  }

  // it's package private to prevent changing id from outside
  // also id is int (not Integer) to prevent setting null
  void setId(int id) {
    this.id = id;
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
