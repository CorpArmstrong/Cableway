package com.itstep.cableway.db.dao.implementation.hibernate;

import com.itstep.cableway.db.dao.UserDao;
import com.itstep.cableway.db.entities.User;
import com.itstep.cableway.db.utils.HibernateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class UserHibernateDao implements UserDao {

    private static final Logger log = LogManager.getLogger(UserDao.class);

    private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private final Lock r = rwl.readLock();
    private final Lock w = rwl.writeLock();

    @Override
    public void create(User user) {
        w.lock();
        try {
            Transaction trns = null;
            Session session = HibernateUtils.getSession();

            try {
                trns = session.beginTransaction();
                session.save(user);
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
    public void save(User user) {
        w.lock();
        try {
            Session session = HibernateUtils.getSession();
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } finally {
            w.unlock();
        }
    }

    @Override
    public void update(User user) {
        w.lock();
        try {
            Transaction trns = null;
            Session session = HibernateUtils.getSession();
            try {
                trns = session.beginTransaction();
                session.update(user);
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
    public void delete(long userId) {
        w.lock();
        try {
            Session session = HibernateUtils.getSession();
            Transaction trns = null;

            try {
                trns = session.beginTransaction();
                User user = session.load(User.class, userId);
                session.delete(user);
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
    public List<User> findAll() {
        r.lock();
        try {
            List list;

            try {
                log.debug("Searching for result of finding all users query");
                Session session = HibernateUtils.getSession();
                list = session.createQuery("from User").list();
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
    public User findById(long id) {
        r.lock();
        try {
            User result = null;

            try {
                Session session = HibernateUtils.getSession();
                result = session.get(User.class, id);
            } catch (HibernateException e) {
                log.error("Error:", e);
            }

            return result;

        } finally {
            r.unlock();
        }
    }
}
