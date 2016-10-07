package com.akhalikov.hibernate;

import org.hibernate.Session;

public class Main {
  public static void main(String[] args) {
    final Session session = HibernateUtil.getSessionFactory().openSession();


  }
}
