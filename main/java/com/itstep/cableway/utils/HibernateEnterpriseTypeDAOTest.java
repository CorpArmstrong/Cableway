package com.itstep.cableway.utils;

/*
import com.itstep.dentalrecords.db.dto.Enterprise;
import com.itstep.dentalrecords.db.dto.EnterpriseType;
import com.itstep.dentalrecords.db.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.exception.JDBCConnectionException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.Assert;

import java.util.Date;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
*/

public class HibernateEnterpriseTypeDAOTest
{
    /*
    HibernateUtils hibernateUtils;
    HibernateEnterpriseTypeDAO hibernateEnterpriseTypeDAO;

    EnterpriseType enterpriseType;
    String testEnterpriseTypeStr;
    Session ss;

    @BeforeClass
    public void init()
    {
        testEnterpriseTypeStr = "Dental Clinic";

        hibernateUtils = new HibernateUtils();
        ss = hibernateUtils.getSession();

        hibernateEnterpriseTypeDAO = new HibernateEnterpriseTypeDAO(hibernateUtils);
        enterpriseType = new EnterpriseType(testEnterpriseTypeStr);
    }

    @Test(expectedExceptions = {JDBCConnectionException.class, Exception.class})
    public void testHibernateSession()
    {
        assertTrue(ss.isOpen());
        assertTrue(ss.isConnected());
    }

    @Test(dependsOnMethods = "testHibernateSession")
    public void testSave()
    {
        boolean checkEnterpriseType = false;
        hibernateEnterpriseTypeDAO.save(enterpriseType);

        List<EnterpriseType> enterpriseTypes = hibernateEnterpriseTypeDAO.findAll();

        if (enterpriseTypes != null)
        {
            for (EnterpriseType temp : enterpriseTypes)
            {
                if (temp.getEnterpriseType().equals(testEnterpriseTypeStr))
                {
                    checkEnterpriseType = true;
                }
            }
        }

        assertTrue(checkEnterpriseType);
    }
    */
}