package com.example.springboot.controller;

import com.example.springboot.UserDetailsImpl;
import com.example.springboot.UserDetailsServiceImpl;
import com.example.springboot.UserRepository;
import com.example.springboot.form.CrudUserForm;
import com.example.springboot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/crud-user")
public class CrudUserController {

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    private final UserRepository userRepository;

    @Autowired
    public CrudUserController(UserDetailsServiceImpl userDetailsServiceImpl, UserRepository userRepository) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String index(Model model) {
        List<User> list = (List<User>) userRepository.findAll();
        model.addAttribute("crudUserList", list);
        return "crud-user/index";
    }

    @GetMapping("/form")
    public String form(@ModelAttribute CrudUserForm crudUserForm) {
        return "crud-user/form";
    }

    @PostMapping("/form")
    public String create(CrudUserForm crudUserForm, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            userDetailsServiceImpl.register(crudUserForm.getName(), crudUserForm.getEmail(), crudUserForm.getPassword(), "ROLE_USER");
        } catch (DataAccessException e) {
            System.out.println(e);
            return "redirect:/crud-user";
        }
        return "redirect:/crud-user";
    }

    @GetMapping("/edit/{id}")
    public String edit(@ModelAttribute CrudUserForm crudUserForm, @PathVariable int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            crudUserForm.setId(user.get().getId());
            crudUserForm.setName(user.get().getName());
            crudUserForm.setEmail(user.get().getEmail());
            return "crud-user/edit";
        } else {
            return "redirect:/crud-user";
        }
    }

    @PostMapping("/edit/{id}")
    public String update(CrudUserForm crudUserForm, @PathVariable int id) {
        User user = new User();
        user.setId(id);
        user.setName(crudUserForm.getName());
        user.setEmail(crudUserForm.getEmail());
        user.setAuthority("ROLE_USER");
        userRepository.save(user);
        return "redirect:/crud-user";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        userRepository.deleteById(id);
        return "redirect:/crud-user";
    }
}
