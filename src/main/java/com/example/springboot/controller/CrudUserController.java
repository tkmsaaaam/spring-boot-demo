package com.example.springboot.controller;

import com.example.springboot.UserDetailsImpl;
import com.example.springboot.UserDetailsServiceImpl;
import com.example.springboot.form.CrudUserForm;
import com.example.springboot.model.User;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Controller
@RequestMapping("/crud-user")
public class CrudUserController {

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @GetMapping
    public String index(Model model) {
        List<User> userList = userDetailsServiceImpl.findAll();
        model.addAttribute("userList", userList);
        return "crud-user/index";
    }

    @GetMapping("/form")
    public String form(@ModelAttribute CrudUserForm crudUserForm) {
        return "crud-user/form";
    }

    @PostMapping("/form")
    public String create(@Validated CrudUserForm crudUserForm, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            userDetailsServiceImpl.register(crudUserForm.getName(), crudUserForm.getEmail(), crudUserForm.getPassword(), "ROLE_USER");
        } catch (DataAccessException e) {
            System.out.println(e);
        }
        return "redirect:/crud-user";
    }

    @GetMapping("/edit/{id}")
    public String edit(@ModelAttribute CrudUserForm crudUserForm, @PathVariable int id) {
        Optional<User> user = userDetailsServiceImpl.findById(id);
        if (user.isPresent()) {
            crudUserForm.setId(user.get().getId());
            crudUserForm.setName(user.get().getName());
            crudUserForm.setEmail(user.get().getEmail());
            return "crud-user/edit";
        } else {
            return "redirect:/crud-user/form";
        }
    }

    @PostMapping("/edit/{id}")
    public String update(@Validated CrudUserForm crudUserForm, @PathVariable int id) {
        User user = User.builder()
                .id(id)
                .name(crudUserForm.getName())
                .email(crudUserForm.getEmail())
                .authority("ROLE_USER")
                .build();
        userDetailsServiceImpl.update(user);
        return "redirect:/crud-user";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        userDetailsServiceImpl.deleteById(id);
        return "redirect:/crud-user";
    }
}
