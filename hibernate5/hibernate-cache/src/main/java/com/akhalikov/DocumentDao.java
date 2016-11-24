package com.akhalikov;

import com.akhalikov.entity.Document;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class DocumentDao {
  private final SessionFactory sessionFactory;

  public DocumentDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public List<Document> getDocuments() {
    return sessionFactory.openSession()
        .createNativeQuery("SELECT * FROM document", Document.class)
        .getResultList();
  }

  Document getDocument(int documentId) {
    try (Session session = sessionFactory.openSession()) {
      return session.get(Document.class, documentId);
    }
  }
}
