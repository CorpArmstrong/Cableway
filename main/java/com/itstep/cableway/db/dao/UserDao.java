package com.itstep.cableway.db.dao;

import com.itstep.cableway.db.entities.User;

import java.util.List;

public interface UserDao {
    void create(User user);

    void save(User user);

    void update(User user);

    void delete(long userId);

    List<User> findAll();

    User findById(long id);
}
