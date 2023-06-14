package com.example.springboot.controller.api;

import com.example.springboot.Entity.User;
import com.example.springboot.form.api.UserForm;
import com.example.springboot.record.ResponseUser;
import com.example.springboot.service.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Controller
@RequestMapping(path = "/api/user")
public class UserController {
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @PostMapping(path = "/add")
    public @ResponseBody String add(@ModelAttribute UserForm userForm) {
        User n = User.builder().name(userForm.getName()).email(userForm.getEmail()).build();
        userDetailsServiceImpl.register(n);
        return "Saved";
    }

    @GetMapping("/{id}")
    public @ResponseBody String get(@PathVariable int id) {
        Optional<User> u = userDetailsServiceImpl.findById(id);
        if (u.isPresent()) {
            return u.get().getEmail();
        } else {
            return "Error";
        }
    }

    @GetMapping(path = "/all")
    public @ResponseBody List<ResponseUser> getAll() {
        return userDetailsServiceImpl.findAll()
                .stream()
                .map(user -> new ResponseUser(user.getId(), user.getName(), user.getEmail(), user.getAuthority()))
                .collect(Collectors.toList());
    }

    @PostMapping(path = "/edit/{id}")
    public @ResponseBody String edit(@ModelAttribute UserForm userForm, @PathVariable int id) {
        User user = User.builder().id(id).name(userForm.getName()).email(userForm.getEmail()).build();
        userDetailsServiceImpl.update(user);
        return "Updated";
    }

    @PostMapping("/delete/{id}")
    public @ResponseBody String delete(@PathVariable int id) {
        userDetailsServiceImpl.deleteById(id);
        return "Deleted";
    }
}
