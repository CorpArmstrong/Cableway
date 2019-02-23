package com.itstep.cableway.db.dao.implementation.hibernate;

import com.itstep.cableway.db.entities.Customer;
import com.itstep.cableway.db.dao.CustomerDao;
import com.itstep.cableway.db.utils.HibernateUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Transaction;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CustomerHibernateDao implements CustomerDao {

    private static final Logger log = LogManager.getLogger(CustomerDao.class);

    private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private final Lock r = rwl.readLock();
    private final Lock w = rwl.writeLock();

    public CustomerHibernateDao() {}

    @Override
    public void create(Customer customer) {
        w.lock();
        try {
            Transaction trns = null;
            Session session = HibernateUtils.getSession();

            try {
                trns = session.beginTransaction();
                session.save(customer);
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
    public void update(Customer customer) {
        w.lock();
        try {
            Transaction trns = null;
            Session session = HibernateUtils.getSession();
            try {
                trns = session.beginTransaction();
                session.update(customer);
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
    public void delete(long customerId) {
        w.lock();
        try {
            Session session = HibernateUtils.getSession();
            Transaction trns = null;

            try {
                trns = session.beginTransaction();
                Customer customer = session.load(Customer.class, customerId);
                session.delete(customer);
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
    public List<Customer> findAll() {
        r.lock();
        try {
            List list;

            try {
                log.debug("Searching for result of finding all customers query");
                Session session = HibernateUtils.getSession();
                list = session.createQuery("from Customer").list();
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
    public List<Customer> fillTableMainWindow() {
        r.lock();
        try {
            List<Customer> list;

            try {
                log.debug("Searching for result of finding all customers query");
                Session session = HibernateUtils.getSession();
                list = session.createQuery("from Customer").list();
            } catch (HibernateException e) {
                log.error("Error:", e);
                return null;
            }

            List<Customer> myList = new ArrayList<Customer>();
            myList.addAll(list);
            return myList;

        } finally {
            r.unlock();
        }
    }

    @Override
    public Customer findById(long id) {
        r.lock();
        try {
            Customer result = null;

            try {
                Session session = HibernateUtils.getSession();
                result = session.get(Customer.class, id);
            } catch (HibernateException e) {
                log.error("Error:", e);
            }

            return result;

        } finally {
            r.unlock();
        }
    }

    @Override
    public boolean findCustomerByCardKey(int cardKey) {
        boolean result = false;

        r.lock();
        try {

            try {
                Session session = HibernateUtils.getSession();
                String queryString = "FROM Customer WHERE cardKey = " + cardKey;
                List<Customer> list = session.createQuery(queryString).list();

                for (Customer tmpCustomer : list) {
                    if (tmpCustomer.getCardKey() == cardKey) {
                        result = true;
                        break;
                    }
                }
            } catch (HibernateException e) {
                log.error("Error:", e);
            }
        } finally {
            r.unlock();
        }

        return result;
    }

    @Override
    public Customer getCustomerByCardKey(int cardKey) {
        r.lock();
        try {
            Customer result = null;

            try {
                Session session = HibernateUtils.getSession();
                String queryString = "FROM Customer WHERE cardKey = " + cardKey;
                List<Customer> list = session.createQuery(queryString).list();

                return list.get(0);

            } catch (HibernateException e) {
                log.error("Error:", e);
            }

            return result;

        } finally {
            r.unlock();
        }
    }

    @Override
    public void save(Customer customer) {
        w.lock();
        try {
            Session session = HibernateUtils.getSession();
            session.beginTransaction();
            session.save(customer);
            session.getTransaction().commit();
        } finally {
            w.unlock();
        }
    }

    @Override
    public long findLastId() {
        r.lock();
        try {
            List list;

            try {
                log.debug("Searching for last id in customers");
                Session session = HibernateUtils.getSession();
                list = session.createQuery("from Customer").list();
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