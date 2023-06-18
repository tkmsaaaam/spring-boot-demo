package com.example.springboot.repository;

import com.example.springboot.Entity.User;

import java.io.Serializable;

public interface UserRepositoryCustom extends Serializable {
    Integer isExisted(String name);

    User findByName(String name);

    void register(User user);

    void update(User user);
}
