package com.example.springboot.repository;

import com.example.springboot.model.User;

import java.io.Serializable;

public interface UserRepositoryCustom extends Serializable {
    boolean isExisted(String name);

    User findByName(String name);

    void register(User user);

    void update(User user);
}
