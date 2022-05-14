package com.example.springboot.form;

public class CrudUserForm {
  private Integer id;
  private String name;
  private String email;
  private String authority;

  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getAuthority() {
    return authority;
  }  
  public void setAuthority(String authority) {
    this.authority = authority;
  }
}
