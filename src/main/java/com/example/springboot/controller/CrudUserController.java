package com.example.springboot.controller;

import com.example.springboot.UserDetailsImpl;
import com.example.springboot.UserDetailsServiceImpl;
import com.example.springboot.form.CrudUserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/crud-user")
public class CrudUserController {
    private final JdbcTemplate jdbcTemplate;

    private final UserDetailsServiceImpl userDetailsServiceImpl;
    @Autowired
    public CrudUserController(JdbcTemplate jdbcTemplate, UserDetailsServiceImpl userDetailsServiceImpl) {
        this.jdbcTemplate = jdbcTemplate;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    @GetMapping
    public String index(Model model) {
        String sql = "SELECT * FROM user";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
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
        String sql = "SELECT * FROM user WHERE id = " + id;
        Map<String, Object> map = jdbcTemplate.queryForMap(sql);
        crudUserForm.setId((int) map.get("id"));
        crudUserForm.setName((String) map.get("name"));
        crudUserForm.setEmail((String) map.get("email"));
        return "crud-user/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(CrudUserForm crudUserForm, @PathVariable int id) {
        String sql = "UPDATE user SET name = ?, email = ?, authority = 'ROLE_USER' WHERE id = " + id;
        jdbcTemplate.update(sql, crudUserForm.getName(), crudUserForm.getEmail());
        return "redirect:/crud-user";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        String sql = "DELETE from user WHERE id = " + id;
        jdbcTemplate.update(sql);
        return "redirect:/crud-user";
    }
}
