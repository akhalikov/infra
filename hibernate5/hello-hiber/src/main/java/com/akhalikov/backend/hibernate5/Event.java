package com.akhalikov.backend.hibernate5;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
@Table(name = "event")
public class Event {
  private Long id;
  private String title;
  private Date date;

  /**
   * For Hibernate
   */
  Event() {
  }

  /**
   * For application use
   */
  public Event(String title, Date date) {
    this.title = title;
    this.date = date;
  }

  @Id
  @GeneratedValue(generator = "increment")
  @GenericGenerator(name = "increment", strategy = "increment")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "EVENT_DATE")
  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  @Override
  public String toString() {
    return "Event (" +
        "id=" + id +
        ", title='" + title + '\'' +
        ", date=" + date +
        ')';
  }
}
