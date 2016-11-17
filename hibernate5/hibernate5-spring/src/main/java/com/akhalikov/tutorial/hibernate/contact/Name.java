package com.akhalikov.tutorial.hibernate.contact;

import javax.persistence.Embeddable;

@Embeddable
class Name {
  private String first;
  private String middle;
  private String last;

  public String getFirst() {
    return first;
  }

  public void setFirst(String first) {
    this.first = first;
  }

  public String getMiddle() {
    return middle;
  }

  public void setMiddle(String middle) {
    this.middle = middle;
  }

  public String getLast() {
    return last;
  }

  public void setLast(String last) {
    this.last = last;
  }
}
