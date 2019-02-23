package com.itstep.cableway.db.dao.implementation.hibernate;

import com.itstep.cableway.db.dao.DefinitionsDao;
import com.itstep.cableway.db.entities.Definitions;
import com.itstep.cableway.db.utils.HibernateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DefinitionsHibernateDao implements DefinitionsDao {

    private static final Logger log = LogManager.getLogger(DefinitionsDao.class);

    private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private final Lock r = rwl.readLock();
    private final Lock w = rwl.writeLock();

    public DefinitionsHibernateDao() {}

    @Override
    public void create(Definitions definitions) {
        w.lock();
        try {
            Transaction trns = null;
            Session session = HibernateUtils.getSession();

            try {
                trns = session.beginTransaction();
                session.save(definitions);
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
    public void save(Definitions definitions) {
        w.lock();
        try {
            Session session = HibernateUtils.getSession();
            session.beginTransaction();
            session.save(definitions);
            session.getTransaction().commit();
        } finally {
            w.unlock();
        }
    }

    @Override
    public void update(Definitions definitions) {
        w.lock();
        try {
            Transaction trns = null;
            Session session = HibernateUtils.getSession();
            try {
                trns = session.beginTransaction();
                session.update(definitions);
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
    public void delete(long definitionId) {
        w.lock();
        try {
            Session session = HibernateUtils.getSession();
            Transaction trns = null;

            try {
                trns = session.beginTransaction();
                Definitions definitions = session.load(Definitions.class, definitionId);
                session.delete(definitions);
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
    public List<Definitions> findAll() {
        r.lock();
        try {
            List list;

            try {
                log.debug("Searching for result of finding all definitions query");
                Session session = HibernateUtils.getSession();
                list = session.createQuery("from Definitions").list();
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
    public Definitions findById(long id) {
        r.lock();
        try {
            Definitions result = null;

            try {
                Session session = HibernateUtils.getSession();
                result = session.get(Definitions.class, id);
            } catch (HibernateException e) {
                log.error("Error:", e);
            }

            return result;

        } finally {
            r.unlock();
        }
    }
}
