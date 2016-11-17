package com.akhalikov.tutorial.hibernate.contact;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "contact")
public class Contact {
  @Id
  private Integer id;

  private Name name;
  private String notes;
  private String website;
  private boolean starred;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Name getName() {
    return name;
  }

  public void setName(Name name) {
    this.name = name;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public String getWebsite() {
    return website;
  }

  public void setWebsite(String website) {
    this.website = website;
  }

  public boolean isStarred() {
    return starred;
  }

  public void setStarred(boolean starred) {
    this.starred = starred;
  }
}
