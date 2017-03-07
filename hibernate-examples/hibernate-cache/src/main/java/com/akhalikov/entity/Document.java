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

import static com.akhalikov.multicache.CacheRegion.LOCAL;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = LOCAL)
@Table(name = "document")
@AttributeOverride(name = "id", column = @Column(name = "document_id", nullable = false))
public class Document {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private Integer id;

  @Column
  private String title;

  @Column
  private String author;

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

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }
}
