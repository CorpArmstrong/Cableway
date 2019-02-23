package com.itstep.cableway.db.dao;

import com.itstep.cableway.db.entities.Subscription;

import java.util.List;

public interface SubscriptionDao {

    void create(Subscription subscription);

    void save(Subscription subscription);

    void update(Subscription subscription);

    void delete(long subscriptionId);

    List<Subscription> findAll();

    Subscription findById(long id);

    List<Subscription> fillSubscriptionTableMainWindow();
}
