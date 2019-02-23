package com.itstep.cableway.db.dao.implementation.hibernate;

import com.itstep.cableway.db.dao.SubscriptionDao;
import com.itstep.cableway.db.entities.Subscription;
import com.itstep.cableway.db.utils.HibernateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SubscriptionHibernateDao implements SubscriptionDao {

    private static final Logger log = LogManager.getLogger(SubscriptionDao.class);

    private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private final Lock r = rwl.readLock();
    private final Lock w = rwl.writeLock();

    public SubscriptionHibernateDao() {}

    @Override
    public void create(Subscription subscription) {
        w.lock();
        try {
            Transaction trns = null;
            Session session = HibernateUtils.getSession();

            try {
                trns = session.beginTransaction();
                session.save(subscription);
                session.getTransaction().commit();
            } catch (RuntimeException e) {
                if (trns != null) {
                    trns.rollback();
                }
                log.error(e);
            }
        } finally {
            w.unlock();
        }
    }

    @Override
    public void save(Subscription subscription) {
        w.lock();
        try {
            Session session = HibernateUtils.getSession();
            session.beginTransaction();
            session.save(subscription);
            session.getTransaction().commit();
        } finally {
            w.unlock();
        }
    }

    @Override
    public void update(Subscription subscription) {
        w.lock();
        try {
            Transaction trns = null;
            Session session = HibernateUtils.getSession();
            try {
                trns = session.beginTransaction();
                session.update(subscription);
                session.getTransaction().commit();
            } catch (RuntimeException e) {
                if (trns != null) {
                    trns.rollback();
                }
                log.error(e);
            }
        } finally {
            w.unlock();
        }
    }

    @Override
    public void delete(long subscriptionId) {
        w.lock();
        try {
            Session session = HibernateUtils.getSession();
            Transaction trns = null;

            try {
                trns = session.beginTransaction();
                Subscription subscription = session.load(Subscription.class, subscriptionId);
                session.delete(subscription);
                session.getTransaction().commit();
            } catch (RuntimeException e) {
                if (trns != null) {
                    trns.rollback();
                }
                log.error(e);
            }
        } finally {
            w.unlock();
        }
    }

    @Override
    public List<Subscription> findAll() {
        r.lock();
        try {
            List list;

            try {
                log.debug("Searching for result of finding all subscriptions query");
                Session session = HibernateUtils.getSession();
                list = session.createQuery("from Subscription").list();
            } catch (HibernateException e) {
                log.error("Error:", e);
                return null;
            }

            return list;

        } finally {
            r.unlock();
        }
    }

    @Override
    public Subscription findById(long id) {
        r.lock();
        try {
            Subscription result = null;

            try {
                Session session = HibernateUtils.getSession();
                result = session.get(Subscription.class, id);
            } catch (HibernateException e) {
                log.error("Error:", e);
            }

            return result;

        } finally {
            r.unlock();
        }
    }

    @Override
    public List<Subscription> fillSubscriptionTableMainWindow() {
        r.lock();
        try {
            List<Subscription> list;

            try {
                log.debug("Searching for result of finding all subscriptions query");
                Session session = HibernateUtils.getSession();
                list = session.createQuery("from Subscription").list();
            } catch (HibernateException e) {
                log.error("Error:", e);
                return null;
            }

            return list;

        } finally {
            r.unlock();
        }
    }
}