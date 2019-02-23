package com.itstep.cableway.db.dao;

import com.itstep.cableway.db.entities.Customer;
import java.util.List;

public interface CustomerDao {

    void create(Customer customer);

    void update(Customer customer);

    void delete(long customerId);

    List<Customer> findAll();

    List<Customer> fillTableMainWindow();

    Customer findById(long id);

    boolean findCustomerByCardKey(int cardKey);

    Customer getCustomerByCardKey(int cardKey);

    void save(Customer myTest);

    long findLastId();
}
