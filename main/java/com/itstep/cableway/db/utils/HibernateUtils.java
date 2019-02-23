package com.itstep.cableway.db.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.*;
import org.hibernate.Session;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.*;

public class HibernateUtils {

    private static final Logger log = LogManager.getLogger(HibernateUtils.class);

    private static Session session;
    private static SessionFactory sessionFactory;

    public HibernateUtils() {}

    public static void initDb() {
        Configuration configuration = new Configuration().configure();
        StandardServiceRegistry service = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();

        sessionFactory = configuration.buildSessionFactory(service);
    }

    public static Session getSession() {
        if(!(session != null && session.isOpen())) {
            session = sessionFactory.openSession();
        }

        return session;
    }

    public static void close() {
        try {
            if (session != null && session.isOpen()) {
                session.close();
                log.info("db connection closed.");
            }
        } catch (HibernateException e) {
            log.info("db connection closed.");
        } catch (Exception e) {
            log.info("db connection closed.");
        }
    }
}