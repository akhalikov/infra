package com.akhalikov.hibernate.model;

public class Vacancy {
  private Integer vacancyId;
  private String title;

  public Vacancy() {
  }

  public Vacancy(Integer vacancyId, String title) {
    this.vacancyId = vacancyId;
    this.title = title;
  }

  public Integer getVacancyId() {
    return vacancyId;
  }

  public void setVacancyId(Integer vacancyId) {
    this.vacancyId = vacancyId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
