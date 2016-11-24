package com.akhalikov.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.AttributeOverride;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "event")
@AttributeOverride(name = "id", column = @Column(name = "event_id", nullable = false))
public class Event {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private Integer id;

  @Column
  private String title;

  @Column(name = "event_date")
  @Temporal(TemporalType.TIMESTAMP)
  private Date date;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

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
