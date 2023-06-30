package com.example.springboot.controller.api;

import com.example.springboot.Entity.User;
import com.example.springboot.form.api.UserForm;
import com.example.springboot.record.ResponseUser;
import com.example.springboot.service.UserDetailsCustomService;
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
    private final UserDetailsCustomService userDetailsCustomService;

    @PostMapping(path = "/add")
    public @ResponseBody ResponseUser add(@ModelAttribute UserForm userForm) {
        User user = User.builder().name(userForm.getName()).email(userForm.getEmail()).build();
        userDetailsCustomService.register(user);
        return new ResponseUser(user.getId(), user.getName(), user.getEmail(), user.getAuthority());
    }

    @GetMapping("/{id}")
    public @ResponseBody Optional<ResponseUser> get(@PathVariable int id) {
        Optional<User> user = userDetailsCustomService.findById(id);
        return user.map(value -> new ResponseUser(value.getId(), value.getName(), value.getEmail(), value.getAuthority()));
    }

    @GetMapping(path = "/all")
    public @ResponseBody List<ResponseUser> getAll() {
        return userDetailsCustomService.findAll()
                .stream()
                .map(user -> new ResponseUser(user.getId(), user.getName(), user.getEmail(), user.getAuthority()))
                .collect(Collectors.toList());
    }

    @PostMapping(path = "/edit/{id}")
    public @ResponseBody ResponseUser edit(@ModelAttribute UserForm userForm, @PathVariable int id) {
        User user = User.builder().id(id).name(userForm.getName()).email(userForm.getEmail()).build();
        userDetailsCustomService.update(user);
        return new ResponseUser(user.getId(), user.getName(), user.getEmail(), user.getAuthority());
    }

    @PostMapping("/delete/{id}")
    public @ResponseBody String delete(@PathVariable int id) {
        userDetailsCustomService.deleteById(id);
        return "Deleted";
    }
}
