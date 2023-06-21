package com.example.springboot.repository;

import com.example.springboot.Entity.User;

import java.io.Serializable;
import java.util.Optional;

public interface UserCustomRepository extends Serializable {
    Integer isExisted(String name);

    Optional<User> findByName(String name);

    void register(User user);

    void update(User user);
}
