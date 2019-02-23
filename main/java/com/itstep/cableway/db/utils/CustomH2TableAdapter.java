package com.itstep.cableway.db.utils;

import com.itstep.cableway.db.dao.implementation.hibernate.*;

public class CustomH2TableAdapter {

    private DefinitionsHibernateDao definitionsHibernateDao;

    private CustomerHibernateDao customerHibernateDao;

    private SubscriptionHibernateDao subscriptionHibernateDao;

    private CablewayDataHibernateDao cablewayDataHibernateDao;

    private UserHibernateDao userHibernateDao;

    public CustomH2TableAdapter() {
        definitionsHibernateDao = new DefinitionsHibernateDao();

        customerHibernateDao = new CustomerHibernateDao();

        subscriptionHibernateDao = new SubscriptionHibernateDao();

        cablewayDataHibernateDao = new CablewayDataHibernateDao();

        userHibernateDao = new UserHibernateDao();

    }

    public DefinitionsHibernateDao getDefinitionsHibernateDao() {
        return definitionsHibernateDao;
    }

    public CustomerHibernateDao getCustomerHibernateDao() {
        return customerHibernateDao;
    }

    public SubscriptionHibernateDao getSubscriptionHibernateDao() {
        return subscriptionHibernateDao;
    }

    public CablewayDataHibernateDao getCablewayDataHibernateDao() {
        return cablewayDataHibernateDao;
    }

    public UserHibernateDao getUserHibernateDao() {
        return userHibernateDao;
    }
}
