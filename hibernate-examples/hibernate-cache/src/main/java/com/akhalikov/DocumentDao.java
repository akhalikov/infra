package com.akhalikov;

import com.akhalikov.entity.Document;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import java.util.List;

public class DocumentDao {
  @Inject
  private SessionFactory sessionFactory;

  List<Document> getDocuments() {
    try (Session session = sessionFactory.openSession()) {
      return session
        .createNativeQuery("SELECT * FROM Document", Document.class)
        .getResultList();
    }
  }

  Document getDocument(int documentId) {
    try (Session session = sessionFactory.openSession()) {
      return session.get(Document.class, documentId);
    }
  }
}
