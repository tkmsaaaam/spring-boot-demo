package com.example.springboot.controller;

import com.example.springboot.UserDetailsServiceImpl;
import com.example.springboot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path = "/api/user")
public class UsersController {
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    public UsersController(UserDetailsServiceImpl userDetailsServiceImpl) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    @PostMapping(path = "/add")
    public @ResponseBody String addNewUser(@RequestParam String name, @RequestParam String email) {
        User n = User.builder().name(name).email(email).build();
        userDetailsServiceImpl.save(n);
        return "Saved";
    }

    @GetMapping("/{id}")
    public @ResponseBody String getUser(@PathVariable int id) {
        Optional<User> u = userDetailsServiceImpl.findById(id);
        if (u.isPresent()) {
            return u.get().getEmail();
        } else {
            return "Error";
        }
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userDetailsServiceImpl.findAll();
    }

    @PostMapping(path = "/edit/{id}")
    public @ResponseBody String editUser(@RequestParam(required = false) String name, @RequestParam(required = false) String email, @PathVariable int id) {
        User user = User.builder().id(id).name(name).email(email).build();
        userDetailsServiceImpl.update(user);
        return "Updated";
    }

    @PostMapping("/delete/{id}")
    public @ResponseBody String deleteUser(@PathVariable int id) {
        userDetailsServiceImpl.deleteById(id);
        return "Deleted";
    }
}
