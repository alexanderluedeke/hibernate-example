package de.apage4u.demo.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
  private static final String HIBERNATE_CFG_XML = "/hibernate.cfg.xml";

  private static SessionFactory sessionFactory = buildSessionFactory();

  private static SessionFactory buildSessionFactory() {
    try {
      if (sessionFactory == null) {
        StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
        Configuration configuration = createConfiguration();
        serviceRegistryBuilder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
      }
      return sessionFactory;
    } catch (Throwable ex) {
      System.err.println("Build SessionFactory failed." + ex);
      throw new ExceptionInInitializerError(ex);
    }
  }

  private static Configuration createConfiguration() {
    return new Configuration().configure(HibernateUtil.class.getResource(HIBERNATE_CFG_XML));
  }

  public static SessionFactory getSessionFactory() {
    return sessionFactory;
  }

  public static void shutdown() {
    getSessionFactory().close();
  }
}