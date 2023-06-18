package com.example.springboot.service;

import com.example.springboot.Entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserDetailsCustomService extends UserDetailsService {
    void register(String username, String email, String password, String authority);

    void register(User user);

    void update(User user);

    boolean isExistUser(String username);

    List<User> findAll();

    Optional<User> findById(int id);

    void deleteById(int id);
}
