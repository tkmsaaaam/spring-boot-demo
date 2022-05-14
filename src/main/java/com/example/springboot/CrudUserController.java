package com.example.springboot;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/crud-user")
public class CrudUserController {
  private JdbcTemplate jdbcTemplate;

  @Autowired
  public CrudUserController(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
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
  public String create(CrudUserForm crudUserForm) {
    String sql = "INSERT INTO user(name, email) VALUES(?, ?);";
    jdbcTemplate.update(sql, crudUserForm.getName(), crudUserForm.getEmail());
    return "redirect:/crud-user";
  }

  @GetMapping("/edit/{id}")
  public String edit(@ModelAttribute CrudUserForm crudUserForm, @PathVariable int id) {
    String sql = "SELECT * FROM user WHERE id = " + id;
    Map<String, Object> map = jdbcTemplate.queryForMap(sql);
    crudUserForm.setId((int)map.get("id"));
    crudUserForm.setName((String)map.get("name"));
    crudUserForm.setEmail((String)map.get("email"));
    return "crud-user/edit";
  }

  @PostMapping("/edit/{id}")
  public String update(CrudUserForm crudUserForm, @PathVariable int id) {
    String sql = "UPDATE user SET name = ?, email = ? WHERE id = " + id;
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
