package akhalikov.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class SessionFactoryBuilder {

  public static SessionFactory createSessionFactory(Class...classes) {
    StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();

    MetadataSources metadataSources = new MetadataSources(serviceRegistryBuilder.build());
    for (Class clazz : classes) {
      metadataSources.addAnnotatedClass(clazz);
    }

    Metadata metadata = metadataSources.getMetadataBuilder().build();

    return metadata.getSessionFactoryBuilder()
      .build();
  }

  private SessionFactoryBuilder() {
  }
}
