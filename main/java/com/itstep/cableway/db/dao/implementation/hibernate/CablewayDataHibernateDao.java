package com.itstep.cableway.db.dao.implementation.hibernate;

import com.itstep.cableway.db.dao.CablewayDataDao;
import com.itstep.cableway.db.entities.CablewayData;
import com.itstep.cableway.db.utils.HibernateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CablewayDataHibernateDao implements CablewayDataDao {

    private static final Logger log = LogManager.getLogger(CablewayDataDao.class);

    private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private final Lock r = rwl.readLock();
    private final Lock w = rwl.writeLock();

    @Override
    public void create(CablewayData cablewayData) {
        w.lock();
        try {
            Transaction trns = null;
            Session session = HibernateUtils.getSession();

            try {
                trns = session.beginTransaction();
                session.save(cablewayData);
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
    public void save(CablewayData cablewayData) {
        w.lock();
        try {
            Session session = HibernateUtils.getSession();
            session.beginTransaction();
            session.save(cablewayData);
            session.getTransaction().commit();
        } finally {
            w.unlock();
        }
    }

    @Override
    public void update(CablewayData cablewayData) {
        w.lock();
        try {
            Transaction trns = null;
            Session session = HibernateUtils.getSession();
            try {
                trns = session.beginTransaction();
                session.update(cablewayData);
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
    public void delete(long cablewayDataId) {
        w.lock();
        try {
            Session session = HibernateUtils.getSession();
            Transaction trns = null;

            try {
                trns = session.beginTransaction();
                CablewayData cablewayData = session.load(CablewayData.class, cablewayDataId);
                session.delete(cablewayData);
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
    public List<CablewayData> findAll() {
        r.lock();
        try {
            List list;

            try {
                log.debug("Searching for result of finding all cableway_data query");
                Session session = HibernateUtils.getSession();
                list = session.createQuery("from CablewayData").list();
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
    public CablewayData findById(long id) {
        r.lock();
        try {
            CablewayData result = null;

            try {
                Session session = HibernateUtils.getSession();
                result = session.get(CablewayData.class, id);
            } catch (HibernateException e) {
                log.error("Error:", e);
            }

            return result;
        } finally {
            r.unlock();
        }
    }

    @Override
    public int calculateNumCableways() {
        r.lock();
        try {
            List list;

            try {
                log.debug("Calculating cableways.");
                Session session = HibernateUtils.getSession();
                list = session.createQuery("from CablewayData").list();
            } catch (HibernateException e) {
                log.error("Error:", e);
                return 0;
            }

            return list.size();

        } finally {
            r.unlock();
        }
    }
}
