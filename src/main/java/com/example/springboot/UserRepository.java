package com.example.springboot;

import org.springframework.data.repository.CrudRepository;

// import com.example.springboot.User;

public interface UserRepository extends CrudRepository<User, Integer> {

}