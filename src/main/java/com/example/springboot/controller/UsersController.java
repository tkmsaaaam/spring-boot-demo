package com.example.springboot.controller;

import com.example.springboot.UserRepository;
import com.example.springboot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path = "/api/user")
public class UsersController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping(path = "/add")
    public @ResponseBody String addNewUser(@RequestParam String name
            , @RequestParam String email) {

        User n = new User();
        n.setName(name);
        n.setEmail(email);
        userRepository.save(n);
        return "Saved";
    }

    @GetMapping("/{id}")
    public @ResponseBody String getUser(@PathVariable int id) {
        Optional<User> u = userRepository.findById(id);
        if (u.isPresent()) {
            return u.get().getEmail();
        } else {
            return "Error";
        }
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping(path = "/edit/{id}")
    public @ResponseBody String editUser(@RequestParam(required = false) String name, @RequestParam(required = false) String email, @PathVariable int id) {
        User u = userRepository.findById(id).get();
        u.setName(name);
        u.setEmail(email);
        userRepository.save(u);
        return "Updated";
    }

    @PostMapping("/delete/{id}")
    public @ResponseBody String deleteUser(@PathVariable int id) {
        User u = userRepository.findById(id).get();
        userRepository.delete(u);
        return "Deleted";
    }
}
