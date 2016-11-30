package com.akhalikov;

import com.akhalikov.entity.Document;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class DocumentDao {
  private final SessionFactory sessionFactory;

  public DocumentDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @SuppressWarnings("unchecked")
  public List<Document> getDocuments() {
    final Session session = sessionFactory.openSession();
    Criteria criteria = session.createCriteria(Document.class);
    List<Document> documents = criteria.list();
    session.close();
    return documents;
  }

  Document getDocument(int documentId) {
    final Session session = sessionFactory.openSession();
    Criteria criteria = session.createCriteria(Document.class);
    criteria.add(Restrictions.eq("id", documentId));
    Document document = (Document) criteria.uniqueResult();
    session.close();
    return document;
  }
}
